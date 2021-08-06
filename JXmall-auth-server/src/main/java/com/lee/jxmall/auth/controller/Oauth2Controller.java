package com.lee.jxmall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lee.common.constant.AuthServerConstant;
import com.lee.common.utils.HttpUtils;
import com.lee.common.utils.R;
import com.lee.jxmall.auth.fegin.MemberFeignService;
import com.lee.common.to.MemberRespVo;
import com.lee.jxmall.auth.vo.SocialUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理社交登录
 */
@Slf4j
@Controller
public class Oauth2Controller {

    @Autowired
    MemberFeignService memberFeignService;

    /**
     * 社交登录成功回调
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping("/oauth2/weibo/success")
    public String weiBo(@RequestParam("code") String code, HttpSession session) throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("grant_type", "authorization_code");
        map.put("code", code);
        map.put("redirect_uri", "http://auth.jxmall.com/oauth2/gitee/success");
        map.put("client_id", "255462609");
        map.put("client_secret", "6f0252053d5b3f7aa84deec0d330913f");
        Map<String, String> headers = new HashMap<>();

        //1、根据code换取AccessToken
        //HttpResponse response = HttpUtils.doPost("gitee.com", "/oauth/token", "post", null, null, map);
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", headers, null, map);
        //2、处理
        if(response.getStatusLine().getStatusCode() == 200){
            // 获取响应体： AccessToken
            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);

            // 相当于知道了当前是那个用户
            // 1.如果用户是第一次进来     自动注册进来(为当前社交用户生成一个会员信息，以后这个账户就会关联这个账号)
            R login = memberFeignService.oauthLogin(socialUser);
            if(login.getCode() == 0){
                MemberRespVo rsepVo = login.getData("data" ,new TypeReference<MemberRespVo>() {});

                log.info("\n欢迎 [" + rsepVo.getUsername() + "] 使用社交账号登录");
                // 第一次使用session 命令浏览器保存这个用户信息 JESSIONSEID 每次只要访问这个网站就会带上这个cookie
                // 在发卡的时候扩大session作用域 (指定域名为父域名)
                // TODO 1.默认发的当前域的session (需要解决子域session共享问题)
                // TODO 2.使用JSON的方式序列化到redis
                //				new Cookie("JSESSIONID","").setDomain("gulimall.com");
                session.setAttribute(AuthServerConstant.LOGIN_USER, rsepVo);

                //3、登录成功跳回首页
                return "redirect:http://jxmall.com";
            }else{
                return "redirect:http://auth.gulimall.com/login.html";
            }
        }else{
            return "redirect:http://auth.gulimall.com/login.html";
        }


    }
}
