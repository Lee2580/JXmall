package com.lee.jxmall.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class JXmallSessionConfig {

    /**
     * 序列化机制
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){

        return new GenericJackson2JsonRedisSerializer();
    }

    /**
     * 自定义session作用域：整个网站
     * 使用一样的session配置，能保证全网站共享一样的session
     */
    @Bean
    public CookieSerializer cookieSerializer() {

        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        //自定义
        //  扩大session作用域，也就是cookie的有效域
        defaultCookieSerializer.setDomainName("jxmall.com");
        //  cookie的键
        defaultCookieSerializer.setCookieName("JXSESSION");

        return defaultCookieSerializer;
    }

}
