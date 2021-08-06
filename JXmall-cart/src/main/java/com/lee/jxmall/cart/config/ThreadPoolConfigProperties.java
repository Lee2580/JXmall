package com.lee.jxmall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jxmall.thread")
@Component
@Data
public class ThreadPoolConfigProperties {
    /**
     * 核心线程数量大小
     */
    private Integer coreSize;
    /**
     * 最大数量大小
     */
    private Integer maxSize;
    /**
     * 休眠时长
     */
    private Integer keepAliveTime;
}
