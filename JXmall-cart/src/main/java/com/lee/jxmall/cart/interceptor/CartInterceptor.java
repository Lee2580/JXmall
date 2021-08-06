package com.lee.jxmall.cart.interceptor;

import com.lee.common.constant.AuthServerConstant;
import com.lee.common.constant.CartConstant;
import com.lee.common.constant.DomainConstant;
import com.lee.common.to.MemberRespVo;
import com.lee.jxmall.cart.vo.UserInfoTo;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 *  购物车拦截器
 *      在执行业务之前，判断用户是否登录，并使用ThreadLocal封装UserInfoTo，传递给controller请求
 */
public class CartInterceptor implements HandlerInterceptor {

    /**
     * 静态公共变量
     *    ThreadLocal：同一个线程共享数据
     */
    public static ThreadLocal<UserInfoTo> userInfoToThreadLocal = new InheritableThreadLocal<>();

    /**
     * 目标方法执行之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        UserInfoTo userInfoTo = new UserInfoTo();

        HttpSession session = request.getSession();
        MemberRespVo member = (MemberRespVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (member != null){
            //用户登录
            userInfoTo.setUsername(member.getUsername());
            userInfoTo.setUserId(member.getId());
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                //有 user-key
                if (name.equals(CartConstant.TEMP_USER_COOKIE_NAME)) {
                    userInfoTo.setUserKey(cookie.getValue());
                    userInfoTo.setTempUser(true);
                }
            }
        }
        //没临时用户，自定义一个
        if (ObjectUtils.isEmpty(userInfoTo.getUserKey())) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            userInfoTo.setUserKey(uuid);
        }
        userInfoToThreadLocal.set(userInfoTo);
        return HandlerInterceptor.super.preHandle(request, response, handler);

    }

    /**
     * 业务执行之后
     *      分配临时用户让浏览器保存，保存一个月
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        UserInfoTo userInfoTo = userInfoToThreadLocal.get();
        userInfoToThreadLocal.remove();
        //如果没有，保存一个临时用户
        if (!userInfoTo.isTempUser()) {
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userInfoTo.getUserKey());
            // 设置这个cookie作用域 过期时间
            cookie.setDomain(DomainConstant.MALL_DOMAIN);
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIME_OUT);
            response.addCookie(cookie);
        }
    }
}
