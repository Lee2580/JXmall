package com.lee.jxmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
 */
@MapperScan("com.lee.jxmall.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class JXmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(JXmallProductApplication.class, args);
    }

}
