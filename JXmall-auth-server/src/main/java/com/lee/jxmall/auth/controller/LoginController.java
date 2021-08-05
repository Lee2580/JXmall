package com.lee.jxmall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.lee.common.constant.AuthServerConstant;
import com.lee.common.exception.BizCodeEnum;
import com.lee.common.utils.R;
import com.lee.jxmall.auth.fegin.MemberFeignService;
import com.lee.jxmall.auth.fegin.ThirdPartFeignService;
import com.lee.jxmall.auth.vo.UserRegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class LoginController {

    /**
     * 发送一个请求直接跳转到一个页面
     * SpringMVC viewcontroller：将请求和页面映射
     */

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ThirdPartFeignService thirdPartFeignService;

    @Autowired
    MemberFeignService memberFeignService;

    /**
     * 进行短信验证码发送
     * @param phone
     * @return
     */
    @ResponseBody
    @GetMapping("/sms/regsendcode")
    public R sendCode(@RequestParam("phone") String phone){

        //TODO 1、接口防刷

        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        // 如果不为空，返回错误信息
        if(null != redisCode && redisCode.length() > 0){
            long CuuTime = Long.parseLong(redisCode.split("_")[1]);
            // 60s内不能再发
            if(System.currentTimeMillis() - CuuTime < 60 * 1000){
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }

        //2、验证码再次调用，redis缓存 key-phone value-code  sms:code:电话号
        // 生成验证码
        String code = UUID.randomUUID().toString().substring(0, 6);
        String redis_code = code + "_" + System.currentTimeMillis();
        // redis缓存验证码
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, redis_code , 10, TimeUnit.MINUTES);
        try {// 调用第三方短信服务
            return thirdPartFeignService.sendCode(phone, code);
        } catch (Exception e) {
            log.warn("远程调用不知名错误 [无需解决]");
        }
        return R.ok();
    }

    /**
     * 注册功能
     * @param registerVo
     * @param result
     * @param redirectAttributes 模拟重定向携带数据
     * @return
     */
    @PostMapping("/register") // auth服务
    public String register(@Valid UserRegisterVo registerVo,  // 注册信息
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        //1.判断校验是否通过
        Map<String, String> errors = new HashMap<>();
        if (result.hasErrors()) {
            //1.1 如果校验不通过，则封装校验结果
            result.getFieldErrors().forEach(item -> {
                // 获取错误的属性名和错误信息
                errors.put(item.getField(), item.getDefaultMessage());
                //1.2 将错误信息封装到session中
                redirectAttributes.addFlashAttribute("errors", errors);
            });
            //1.2 重定向到注册页
            return "redirect:http://auth.jxmall.com/reg.html";
        }

        //2 若JSR303校验通过，开始注册  调用远程服务
        //判断验证码是否正确
        String code = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone());
        //2.1 如果对应手机的验证码不为空且与提交的相等->验证码正确
        if (!ObjectUtils.isEmpty(code) && registerVo.getCode().equals(code.split("_")[0])) {
            //2.1.1 使得验证后的验证码失效     令牌机制
            stringRedisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone());

            //2.1.2 远程调用会员服务注册
            R r = memberFeignService.regist(registerVo);
            if (r.getCode() == 0) {
                //调用成功，重定向登录页
                return "redirect:http://auth.jxmall.com/login.html";
            } else {
                //调用失败，返回注册页并显示错误信息
                errors.put("msg", r.getData(new TypeReference<String>() {
                }));
                redirectAttributes.addFlashAttribute("errors", errors);
                return "redirect:http://auth.jxmall.com/reg.html";
            }
        } else {
            //2.2 验证码错误
            errors.put("code", "验证码错误");
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.jxmall.com/reg.html";
        }

    }

}
