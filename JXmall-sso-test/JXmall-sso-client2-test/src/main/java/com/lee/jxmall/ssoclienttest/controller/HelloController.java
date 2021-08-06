package com.lee.jxmall.ssoclienttest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class HelloController {

    @Value("${sso.server.url}")
    String ssoServerUrl;

    /**
     * 无需登录即可访问
     * @return
     */
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    /**
     * 感知这次实在ssoserver登录成功跳回的
     * @RequestParam(value = "token",required = false)  不是必须携带的
     * @param model
     * @param httpSession
     * @param token     只要去ssoserver登录成功调回来就会带上
     * @return
     */
    @GetMapping("/boss")
    public String boss(Model model, HttpSession httpSession,
                            @RequestParam(value = "token",required = false) String token){

        if (!ObjectUtils.isEmpty(token)){
            //跳回来的，登录成功的
            //TODO 获取ssoserver服务器获取当前用户的真正信息
            //      用Http工具类 或 RestTemplate
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> forEntity = restTemplate.getForEntity(
                    "http://ssoserver.com:8080/userInfo?token=" + token, String.class);
            //从响应里获取真正的值
            String body = forEntity.getBody();
            httpSession.setAttribute("loginUser",body);
        }

        Object loginUser = httpSession.getAttribute("loginUser");
        if (loginUser == null){
            //没登陆，跳转登录服务器

            //跳转后，使用url上的查询参数，表示自己是哪个页面
            return "redirect:"+ssoServerUrl+"?redirect_url=http://client2.com:8082/boss";
        }else {
            ArrayList<String> emps = new ArrayList<>();
            emps.add("zhangsan");
            emps.add("lisi");

            model.addAttribute("emps",emps);
            return "list";
        }

    }

}
