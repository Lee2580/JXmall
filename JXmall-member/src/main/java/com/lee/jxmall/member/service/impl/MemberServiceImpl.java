package com.lee.jxmall.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lee.common.utils.HttpUtils;
import com.lee.jxmall.member.dao.MemberLevelDao;
import com.lee.jxmall.member.entity.MemberLevelEntity;
import com.lee.jxmall.member.exception.PhoneExistException;
import com.lee.jxmall.member.exception.UsernameExistException;
import com.lee.jxmall.member.vo.MemberLoginVo;
import com.lee.jxmall.member.vo.MemberRegistVo;
import com.lee.jxmall.member.vo.SocialUser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.member.dao.MemberDao;
import com.lee.jxmall.member.entity.MemberEntity;
import com.lee.jxmall.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 创建会员信息
     * @param vo
     */
    @Override
    public void regist(MemberRegistVo vo) {

        MemberEntity entity = new MemberEntity();
        //将信息插入到数据库即可
        MemberDao dao = this.baseMapper;
        //设置默认数据
        //默认等级
        MemberLevelEntity memberLevelEntity = memberLevelDao.getDefaultLevel();
        entity.setLevelId(memberLevelEntity.getId());

        // 检查手机号 用户名是否唯一
        checkPhoneUnique(vo.getPhone());
        checkUsernameUnique(vo.getUserName());

        entity.setMobile(vo.getPhone());
        entity.setUsername(vo.getUserName());

        // 密码要加密存储
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(bCryptPasswordEncoder.encode(vo.getPassword()));

        // 其他的默认信息
        entity.setCity("外星系 未知地");
        entity.setCreateTime(new Date());
        entity.setStatus(0);
        entity.setNickname(vo.getUserName());
        entity.setBirth(new Date());
        entity.setEmail("xxx@gmail.com");
        entity.setGender(1);
        entity.setJob("JAVA");

        dao.insert(entity);

    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneExistException{
        MemberDao memberDao = this.baseMapper;
        Integer mobile = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (mobile > 0) {
            throw new PhoneExistException();
        }
    }

    @Override
    public void checkUsernameUnique(String username) throws UsernameExistException{
        MemberDao memberDao = this.baseMapper;
        Integer count = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("username", username));
        if (count>0) {
            throw new UsernameExistException();
        }
    }

    /**
     * 登录方法
     * @param loginVo
     * @return
     */
    @Override
    public MemberEntity login(MemberLoginVo loginVo) {

        String loginUser = loginVo.getLoginUser();
        String password = loginVo.getPassword();
        //以用户名或电话号登录的进行查询
        MemberDao memberDao = this.baseMapper;
        MemberEntity entity = memberDao.selectOne(new QueryWrapper<MemberEntity>().
                eq("username", loginUser).or().eq("mobile", loginUser));

        if (entity!=null){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            //密码匹配
            boolean matches = bCryptPasswordEncoder.matches(password, entity.getPassword());
            if (matches){
                entity.setPassword("");
                return entity;
            }
        }
        return null;
    }

    /**
     * 社交账号登录
     * @param socialUser
     * @return
     */
    @Override
    public MemberEntity login(SocialUser socialUser) {

        //登录和注册合并逻辑

        // 微博是uid，gitee没有uid
        String uid = socialUser.getUid();
        // 1.判断社交用户登录过系统
        MemberDao dao = this.baseMapper;
        MemberEntity entity = dao.selectOne(new QueryWrapper<MemberEntity>().eq("social_uid", uid));

        MemberEntity memberEntity = new MemberEntity();

        if(entity != null){
            // 说明这个用户注册过, 修改它的资料
            // 更新令牌
            memberEntity.setId(entity.getId());
            memberEntity.setAccessToken(socialUser.getAccessToken());
            memberEntity.setExpiresIn(socialUser.getExpiresIn());
            // 更新
            dao.updateById(memberEntity);

            entity.setAccessToken(socialUser.getAccessToken());
            entity.setExpiresIn(socialUser.getExpiresIn());
            entity.setPassword(null);
            return entity;
        }else {
            // 没有注册过
            // 2. 没有查到当前社交用户对应的记录 我们就需要注册一个
            HashMap<String, String> map = new HashMap<>();
            map.put("access_token", socialUser.getAccessToken());
            map.put("uid", socialUser.getUid());
            try {
                // 3. 查询当前社交用户账号信息(昵称、性别、头像等)
                HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<>(), map);
                if (response.getStatusLine().getStatusCode() == 200) {
                    // 查询成功
                    String json = EntityUtils.toString(response.getEntity());
                    // 这个JSON对象什么样的数据都可以直接获取
                    JSONObject jsonObject = JSON.parseObject(json);
                    memberEntity.setNickname(jsonObject.getString("name"));
                    memberEntity.setUsername(jsonObject.getString("name"));
                    memberEntity.setGender("m".equals(jsonObject.getString("gender")) ? 1 : 0);
                    memberEntity.setCity(jsonObject.getString("location"));
                    memberEntity.setJob("自由职业");
                    memberEntity.setEmail(jsonObject.getString("email"));
                }
            } catch (Exception e) {
                log.warn("社交登录时远程调用出错 [尝试修复]");
            }
            memberEntity.setStatus(0);
            memberEntity.setCreateTime(new Date());
            memberEntity.setBirth(new Date());
            memberEntity.setLevelId(1L);
            memberEntity.setSocialUid(socialUser.getUid());
            memberEntity.setAccessToken(socialUser.getAccessToken());
            memberEntity.setExpiresIn(socialUser.getExpiresIn());

            // 注册 -- 登录成功
            dao.insert(memberEntity);
            memberEntity.setPassword(null);
            return memberEntity;
        }
    }

}
