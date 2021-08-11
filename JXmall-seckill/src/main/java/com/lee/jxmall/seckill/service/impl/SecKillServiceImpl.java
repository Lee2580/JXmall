package com.lee.jxmall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lee.common.utils.R;
import com.lee.jxmall.seckill.feign.CouponFeignService;
import com.lee.jxmall.seckill.feign.ProductFeignService;
import com.lee.jxmall.seckill.service.SecKillService;
import com.lee.jxmall.seckill.to.SecKillSkuRedisTo;
import com.lee.jxmall.seckill.vo.SecKillSessionWithSkusVo;
import com.lee.jxmall.seckill.vo.SkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    RedissonClient redissonClient;

    private final String SESSION_CACHE_PREFIX = "seckill:sessions:";
    private final String SECKILL_CHARE_PREFIX = "seckill:skus:";
    //后面+商品随机码
    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";

    /**
     * 上架秒杀商品
     */
    @Override
    public void uploadSecKillSkuLatest3Days() {

        //1、扫描最近三天的商品需要参加秒杀的活动
        R session = couponFeignService.getLates3DaySession();
        if (session.getCode() == 0) {
            //上架商品
            List<SecKillSessionWithSkusVo> sessionData = session.getData(
                    new TypeReference<List<SecKillSessionWithSkusVo>>() {
            });
            //缓存到Redis
            //1、缓存活动信息
            saveSessionInfos(sessionData);

            //2、缓存活动的关联商品信息
            saveSessionSkuInfo(sessionData);
        }
    }

    /**
     * 缓存秒杀活动信息
     *
     * @param sessions
     */
    private void saveSessionInfos(List<SecKillSessionWithSkusVo> sessions) {

        if (!CollectionUtils.isEmpty(sessions)) {
            sessions.stream().forEach(session -> {

                //获取当前活动的开始和结束时间的时间戳
                long startTime = session.getStartTime().getTime();
                long endTime = session.getEndTime().getTime();

                //存入到Redis中的key
                String key = SESSION_CACHE_PREFIX + startTime + "_" + endTime;

                //判断Redis中是否有该信息，如果没有才进行添加
                Boolean hasKey = stringRedisTemplate.hasKey(key);
                //缓存活动信息
                if (!hasKey) {
                    //获取到活动中所有商品的skuId
                    List<String> skuIds = session.getRelationSkus().stream().map(item ->
                            item.getPromotionSessionId() + "-" + item.getSkuId().toString()).collect(Collectors.toList());
                    stringRedisTemplate.opsForList().leftPushAll(key, skuIds);
                }
            });
        }
    }

    /**
     * 缓存秒杀活动所关联的商品信息
     */
    private void saveSessionSkuInfo(List<SecKillSessionWithSkusVo> sessions) {

        if (!CollectionUtils.isEmpty(sessions)){
            sessions.stream().forEach(session -> {
                //准备hash操作，绑定hash
                BoundHashOperations<String, Object, Object> operations = stringRedisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
                session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                    //生成随机码
                    String token = UUID.randomUUID().toString().replace("-", "");
                    String redisKey = seckillSkuVo.getPromotionSessionId().toString() + "-" + seckillSkuVo.getSkuId().toString();
                    if (!operations.hasKey(redisKey)) {

                        //缓存我们商品信息
                        SecKillSkuRedisTo redisTo = new SecKillSkuRedisTo();
                        Long skuId = seckillSkuVo.getSkuId();
                        //1、先查询sku的基本信息，调用远程服务
                        R info = productFeignService.getSkuInfo(skuId);
                        if (info.getCode() == 0) {
                            SkuInfoVo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                            });

                            redisTo.setSkuInfo(skuInfo);
                        }

                        //2、sku的秒杀信息
                        BeanUtils.copyProperties(seckillSkuVo, redisTo);

                        //3、设置当前商品的秒杀时间信息
                        redisTo.setStartTime(session.getStartTime().getTime());
                        redisTo.setEndTime(session.getEndTime().getTime());

                        //4、设置商品的随机码（防止恶意攻击）
                        redisTo.setRandomCode(token);

                        //序列化json格式存入Redis中
                        String seckillValue = JSON.toJSONString(redisTo);
                        operations.put(seckillSkuVo.getPromotionSessionId().toString() + "-" + seckillSkuVo.getSkuId().toString(), seckillValue);

                        //如果当前这个场次的商品库存信息已经上架就不需要上架
                        //5、使用库存作为分布式Redisson信号量（限流）
                        // 使用库存作为分布式信号量
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                        // 商品可以秒杀的数量作为信号量
                        semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                    }
                });
            });
        }
    }
}
