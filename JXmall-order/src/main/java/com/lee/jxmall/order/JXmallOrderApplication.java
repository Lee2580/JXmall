package com.lee.jxmall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 *  RabbitMQ
 *      1、引入amqp ，RabbitAutoConfiguration就会自动生效
 *      2、给容器中自动配置了
 *          rabbitTemplate、AmqpAdmin、CachingConnectionFactory、RabbitMessagingTemplate
 *          所有的属性都是在 spring.rabbitmq 进行绑定
 *              @ConfigurationProperties(prefix = "spring.rabbitmq")
 *              public class RabbitProperties{}
 *      3、配置spring.rabbitmq信息
 *      4、@EnableRabbit
 *      5、监听消息  使用@RabbitListener，必须有@EnableRabbit
 *          @RabbitListener：类+方法上 （监听哪些队列）
 *          @RabbitHandler：方法上  （重载区分不同的消息）
 *
 *  本地事务失效问题
 *      同一个对象内事务方法互调事务设置失效，原因是绕过了代理对象
 *  解决：
 *      1、引入aop，aop-starter; spring-boot-starter-aop引入aspectj
 *      2、@EnableAspectjAutoProxy(exposeProxy = true)开启 aspectj动态代理功能，即使没有接口也可以创建动态代理
 *              对外暴露代理对象
 *      3、本类互调用代理对象
 *
 */
@EnableFeignClients
@EnableRedisHttpSession
@EnableDiscoveryClient
@EnableRabbit
@SpringBootApplication
public class JXmallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(JXmallOrderApplication.class, args);
    }

}
