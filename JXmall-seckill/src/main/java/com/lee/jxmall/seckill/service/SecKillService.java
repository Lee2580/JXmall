package com.lee.jxmall.seckill.service;

import com.lee.jxmall.seckill.to.SecKillSkuRedisTo;

import java.util.List;

public interface SecKillService {
    void uploadSecKillSkuLatest3Days();

    List<SecKillSkuRedisTo> getCurrentSecKillSkus();

    SecKillSkuRedisTo getSkuSecKilInfo(Long skuId);

    String kill(String killId, String key, Integer num) throws InterruptedException;
}
