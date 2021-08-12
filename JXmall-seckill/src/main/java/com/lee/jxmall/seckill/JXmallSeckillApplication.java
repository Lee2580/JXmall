package com.lee.jxmall.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 *  1、整合Sentinel
 *      1）、引入依赖
 *      2）、下载控制台
 *      3）、配置Sentinel控制台地址
 *      4）、在控制台调整参数
 *          默认所有流控设置保存在内存中，重启失效
 *
 *  2、导入actuator，配置management.endpoints.web.exposure.include: *
 *  3、自定义sentinel流控返回数据
 *  4、使用sentinel保护feign远程调用，熔断
 *      1）、调用方的熔断保护     feign.sentinel.enabled: true
 *      2）、调用方手动指定远程服务的降级策略，控制台设置   远程服务被降级处理，触发熔断回调方法
 *      3）、超大浏览时，必须牺牲一些远程服务。在服务提供方（远程服务）指定降级策略
 *          提供方在运行，但是不运行自己的业务逻辑，返回的是默认的降级数据（限流的数据）
 *
 *  5、自定义受保护的资源
 *      1）、代码方式
 *          try (Entry entry = SphU.entry("secKillSkus")) {}catch(Exception e){}
 *      2)、注解方式
 *          @SentinelResource(value = "getCurrentSecKillSkus",blockHandler = "blockHandler")
 *
 *      这两种方式都要配置被限流后的默认返回，url请求可以设置同一返回
 *
 *
 */
@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class JXmallSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(JXmallSeckillApplication.class, args);
    }

}
