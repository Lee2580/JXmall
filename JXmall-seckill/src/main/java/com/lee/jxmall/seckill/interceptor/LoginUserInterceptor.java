package com.lee.jxmall.seckill.interceptor;

import com.lee.common.constant.AuthServerConstant;
import com.lee.common.to.MemberRespVo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    //为了共享数据
    public static ThreadLocal<MemberRespVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("/kill", uri);
        //秒杀拦截，其他放行
        if (match) {
            // 获取session
            HttpSession session = request.getSession();
            // 获取登录用户
            MemberRespVo memberRespVo = (MemberRespVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
            if (memberRespVo != null) {
                loginUser.set(memberRespVo);
                return true;
            } else {
                // 没登陆就去登录
                session.setAttribute("msg", AuthServerConstant.NOT_LOGIN);
                response.sendRedirect("http://auth.jxmall.com/login.html");
                return false;
            }
        }
        return true;
    }
}
