package com.lee.jxmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.lee.common.exception.BizCodeEnum;
import com.lee.jxmall.member.exception.PhoneExistException;
import com.lee.jxmall.member.exception.UsernameExistException;
import com.lee.jxmall.member.feign.CouponFeignService;
import com.lee.jxmall.member.vo.MemberLoginVo;
import com.lee.jxmall.member.vo.MemberRegistVo;
import com.lee.jxmall.member.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lee.jxmall.member.entity.MemberEntity;
import com.lee.jxmall.member.service.MemberService;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.R;



/**
 * 会员
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 14:40:29
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    @RequestMapping("/coupon")
    public R test(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("zhangsan");

        R r = couponFeignService.memberCoupon();
        //前面是本地查询到的，后面是远程服务查询到的
        return R.ok().put("member",memberEntity).put("coupon",r.get("coupon"));
    }


    /**
     * 社交登录
     * @param socialUser
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody SocialUser socialUser) {
        MemberEntity entity=memberService.login(socialUser);

        if (entity!=null){
            return R.ok();
        }else {
            return R.error(BizCodeEnum.LOGINUSER_PASSWORD_ERROR.getCode(), BizCodeEnum.LOGINUSER_PASSWORD_ERROR.getMsg());
        }
    }

    /**
     * 登录
     * @param loginVo
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo loginVo) {
        MemberEntity entity=memberService.login(loginVo);

        if (entity!=null){
            return R.ok();
        }else {
            return R.error(BizCodeEnum.LOGINUSER_PASSWORD_ERROR.getCode(), BizCodeEnum.LOGINUSER_PASSWORD_ERROR.getMsg());
        }
    }

    /**
     * 注册会员信息
     * @param vo
     * @return
     */
    @PostMapping("/regist")
    public R regist(@RequestBody MemberRegistVo vo){
        try {
            memberService.regist(vo);
        } catch (UsernameExistException userException) {
            //用户已存在
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(), BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        } catch (PhoneExistException phoneException) {
            // 手机已经注册
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        }

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
