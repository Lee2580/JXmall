package com.lee.jxmall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MyRedissonConfig {

    /**
     * 所有对redisson的使用都是通过RedissonClient对象
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        //1、创建配置
        Config config = new Config();
        //单节点模式
        config.useSingleServer().setAddress("redis://192.168.79.10:6379").setPassword("li15854198694");
        /*
        //集群模式
        config.useClusterServers()
                .addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");*/

        //2、根据config创建出RedissonClient实例
        return Redisson.create(config);
    }


}
