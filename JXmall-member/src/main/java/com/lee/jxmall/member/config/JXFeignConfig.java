package com.lee.jxmall.member.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class JXFeignConfig {

    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor(){
            /**
             *  feign远程调用之前先进行RequestInterceptor.apply
             *      RequestContextHolder为SpringMVC中共享request数据的上下文，底层由ThreadLocal实现
             *      也就是说该请求只对当前访问线程有效，如果new了新线程就找不到原来request了
             * @param requestTemplate
             */
            @Override
            public void apply(RequestTemplate requestTemplate) {
                //1. 使用RequestContextHolder拿到老请求的请求数据
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (requestAttributes != null) {
                    //老请求
                    HttpServletRequest request = requestAttributes.getRequest();
                    if (request != null) {
                        //2. 将老请求得到cookie信息放到feign的新请求上
                        String cookie = request.getHeader("Cookie");
                        requestTemplate.header("Cookie", cookie);
                    }
                }
            }
        };
    }
}
