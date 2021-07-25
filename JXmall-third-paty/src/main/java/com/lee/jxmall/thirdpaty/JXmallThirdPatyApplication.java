package com.lee.jxmall.thirdpaty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class JXmallThirdPatyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JXmallThirdPatyApplication.class, args);
    }

}
