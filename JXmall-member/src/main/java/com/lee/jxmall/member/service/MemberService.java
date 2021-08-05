package com.lee.jxmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.member.entity.MemberEntity;
import com.lee.jxmall.member.exception.PhoneExistException;
import com.lee.jxmall.member.exception.UsernameExistException;
import com.lee.jxmall.member.vo.MemberLoginVo;
import com.lee.jxmall.member.vo.MemberRegistVo;
import com.lee.jxmall.member.vo.SocialUser;

import java.util.Map;

/**
 * 会员
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 14:40:29
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(MemberRegistVo vo);

    void checkPhoneUnique(String phone) throws PhoneExistException;

    void checkUsernameUnique(String username) throws UsernameExistException;

    MemberEntity login(MemberLoginVo loginVo);

    MemberEntity login(SocialUser socialUser);
}

