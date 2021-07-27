package com.lee.jxmall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//开启事务
@EnableTransactionManagement
@MapperScan("com.lee.jxmall.ware.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class JXmallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(JXmallWareApplication.class, args);
    }

}
