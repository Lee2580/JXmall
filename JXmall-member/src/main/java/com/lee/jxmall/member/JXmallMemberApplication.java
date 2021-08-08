package com.lee.jxmall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/*
    远程调用别的服务
        1、引入openfeign
        2、编写接口，调用远程服务
            1、声明接口的每个方法都是调用哪个远程服务的哪个请求
        3、开启远程调用
 */
@EnableRedisHttpSession
@EnableFeignClients(basePackages = "com.lee.jxmall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class JXmallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(JXmallMemberApplication.class, args);
    }

}
