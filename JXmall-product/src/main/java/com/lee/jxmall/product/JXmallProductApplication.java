package com.lee.jxmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
    整合mybatis-plus
        1、导入依赖
        2、配置
            1、数据源
                1、数据库驱动
                2、配置数据源相关信息
            2、mybits-plus
                1、使用@MapperScan
                2、sql映射文件
     逻辑删除
        1、配置全局逻辑删除规则（可省略）
        2、配置逻辑删除的组件（3.1以上省略）
        3、加逻辑删除注解 @TableLogic

    模板引擎
        1、关闭缓存  thymeleaf: cache: false
        2、静态资源放在static文件夹下，可以按照路径直接访问
        3、页面放在templates下，直接访问
            springboot 访问项目时，默认找index
        4、页面修改不重启服务器实时更新
            1、dev-tools
            2、ctrl+F9
 */
@EnableFeignClients(basePackages ="com.lee.jxmall.product.feign")
@MapperScan("com.lee.jxmall.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class JXmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(JXmallProductApplication.class, args);
    }

}
