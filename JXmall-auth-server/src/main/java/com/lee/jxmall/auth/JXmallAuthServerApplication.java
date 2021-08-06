package com.lee.jxmall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 *  SpringSession 核心原理
 *      1、@EnableRedisHttpSession导入RedisHttpSessionConfiguration配置
 *          1）、给容器中添加了一个组件
 *              SessionRepository --> RedisIndexedSessionRepository ==> redis操作session，session的增删改查封装类
 *          2）、springSessionRepositoryFilter --> Filter ：session存储过滤器,每个请求过来都要经过filter
 *              2.1)、创建的适合就自动从容器中获取到了SessionRepository
 *              2.2)、原生的request，response都被包装成了SessionRepositoryRequestWrapper，SessionRepositoryResponseWrapper
 *              2.3）、以后获取session。 request.getSession();
 *              2.4）、要用wrappedRequest.getSession(); 他自己重写了session ==> SessionRepository中获取到的
 *
 *      装饰者模式
 *
 *      浏览器不关，session是自动延期的，redis中数据有过期时间
 */
//整合redis作为session存储
@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class JXmallAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JXmallAuthServerApplication.class, args);
    }

}
