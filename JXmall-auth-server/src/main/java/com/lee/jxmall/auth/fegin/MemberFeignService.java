package com.lee.jxmall.auth.fegin;

import com.lee.common.utils.R;
import com.lee.jxmall.auth.vo.UserLoginVo;
import com.lee.jxmall.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("jxmall-member")
public interface MemberFeignService {

    @PostMapping("/member/member/regist")
    R regist(@RequestBody UserRegisterVo vo);

    /**
     * 传的是json， 要加 @RequestBody
     * @param loginVo
     * @return
     */
    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo loginVo);
}
