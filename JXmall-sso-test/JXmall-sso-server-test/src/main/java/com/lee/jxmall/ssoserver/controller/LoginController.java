package com.lee.jxmall.ssoserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @ResponseBody
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("token") String token){
        String s = stringRedisTemplate.opsForValue().get("token");

        return s;
    }

    @GetMapping("/login.html")
    public String loginPage(@RequestParam("redirect_url") String url, Model model,
                            @CookieValue(value = "sso_token",required = false) String sso_token){

        if (!ObjectUtils.isEmpty(sso_token)){
            //说明之前登录过
            return "redirect:"+url+"?token="+sso_token;
        }
        model.addAttribute("url",url);
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("url") String url,
                          HttpServletResponse response){

        if (!ObjectUtils.isEmpty(username)&&!ObjectUtils.isEmpty(password)){
            //登录成功，跳回之前页面
            //把成功登录的用户存起来
            String uuid = UUID.randomUUID().toString().replace("_", "");
            stringRedisTemplate.opsForValue().set(uuid,username);
            //登录成功，给当前服务器留一个cookie
            Cookie cookie = new Cookie("sso_token",uuid);
            response.addCookie(cookie);
            //携带令牌
            return "redirect:"+url+"?token="+uuid;
        }
        //登录失败，跳回登录页
        return "login";
    }
}
