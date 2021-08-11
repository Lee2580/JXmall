package com.lee.jxmall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
    1、使用nacos作为配置中心
        1、引入依赖
        2、配置bootstrap.properties/bootstrap.yaml
            spring.application.name=jxmall-coupon
            spring.cloud.nacos.config.server-addr=192.168.79.10:8848
        3、给配置中心默认添加数据集：jxmall-coupon.properties/jxmall-coupon.yaml
        4、给应用名.properties（/.yaml）添加配置
        5、动态获取配置
            @RefreshScope：动态获取并刷新配置
            @Value("${配置项名})；获取到配置
            优先使用配置中心的配置

     2、细节
        1、命名空间
            用作配置隔离。（一般每个微服务一个命名空间）
            默认public。默认新增的配置都在public空间下
            开发、测试、开发可以用命名空间分割。properties每个空间有一份
            spring.cloud.nacos.config.namespace=对应的空间id

            为每个微服务配置一个命名空间，微服务互相隔离
        2、配置集：所有配置的集合
        3、配置集ID：类似于配置文件名，即Data ID
        4、配置分组
            默认所有的配置集都属于DEFAULT_GROUP

    最终方案：每个微服务创建自己的命名空间，然后使用配置分组区分环境（dev/test/prod）

    3、同时加载多个配置集
        1、微服务任何配置文件都可以放在配置中心中
        2、在bootstrap.yaml说明加载配置中心哪些配置文件即可
        3、@Value @ConfigurationProperties
            springboot任何方法从配置文件中获取值，都能使用
            配置中心有的优先使用配置中心
 */
@EnableDiscoveryClient
@SpringBootApplication
public class JXmallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(JXmallCouponApplication.class, args);
    }

}
