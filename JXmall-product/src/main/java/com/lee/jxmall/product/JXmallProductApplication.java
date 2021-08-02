package com.lee.jxmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
    1、整合mybatis-plus
        1、导入依赖
        2、配置
            1、数据源
                1、数据库驱动
                2、配置数据源相关信息
            2、mybits-plus
                1、使用@MapperScan
                2、sql映射文件
     2、逻辑删除
        1、配置全局逻辑删除规则（可省略）
        2、配置逻辑删除的组件（3.1以上省略）
        3、加逻辑删除注解 @TableLogic

    3、模板引擎
        1、关闭缓存  thymeleaf: cache: false
        2、静态资源放在static文件夹下，可以按照路径直接访问
        3、页面放在templates下，直接访问
            springboot 访问项目时，默认找index
        4、页面修改不重启服务器实时更新
            1、dev-tools
            2、ctrl+F9

    4、整合redis
        1、引入依赖
        2、配置yaml
        3、使用springboot自动配置好的StringRedisTemplate操作redis

    5、整合redisson作为分布式锁等功能框架
        1、引入依赖
        2、配置redisson
        3、使用

    6、整合SpringCache简化缓存开发
        1、引入依赖
        2、配置
            1）、自动配置
                CacheAuroConfiguration会导入RedisCacheConfiguration
                自动配好了缓存管理器
            2）、配置使用redis作为缓存
        3、测试使用缓存
            @Cacheable: Triggers cache population.
                触发将数据保存到缓存的操作
            @CacheEvict: Triggers cache eviction.
                触发将数据从缓存删除的操作
            @CachePut: Updates the cache without interfering with the method execution.
                不影响方法执行更新缓存
            @Caching: Regroups multiple cache operations to be applied on a method.
                组合以上多个操作
            @CacheConfig: Shares some common cache-related settings at class-level.
                在类级别共享缓存的相同配置
            1）、开启缓存功能 @EnableCaching
            2）、只需要使用注解就能完成缓存操作

        4、原理
            CacheAutoConfiguration --> RedisCacheConfiguration --> 自动配置了RedisCacheManager
            --> 初始化所有的缓存 --> 每个缓存决定使用什么配置 --> 如果RedisCacheConfiguration有就用已有的，
            没有就用默认配置 --> 想改缓存的配置，只需给容器放一个RedisCacheConfiguration即可 -->
            就会应用到当前RedisCacheManager管理的所有缓存分区中


 */
@EnableCaching
@EnableFeignClients(basePackages ="com.lee.jxmall.product.feign")
@MapperScan("com.lee.jxmall.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class JXmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(JXmallProductApplication.class, args);
    }

}
