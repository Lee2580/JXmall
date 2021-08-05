package com.lee.jxmall.thirdpaty.controller;

import com.lee.common.exception.BizCodeEnum;
import com.lee.common.utils.R;
import com.lee.jxmall.thirdpaty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsSendController {

    @Autowired
    private SmsComponent smsComponent;

    /**
     * 提供给别的服务进行调用
     *      该controller是发给短信服务的，不是验证的
     */
    @GetMapping("/sendcode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code){

        if (!"fail".equals(smsComponent.sendSmsCode(phone, code).split("_")[0])) {
            return R.ok();
        }
        return R.error(BizCodeEnum.SMS_SEND_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_SEND_CODE_EXCEPTION.getMsg());
    }

}
