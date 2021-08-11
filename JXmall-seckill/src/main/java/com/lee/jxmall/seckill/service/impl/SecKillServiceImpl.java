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

import java.security.KeyStore;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
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
     * 查询当前时间可以参与秒杀的商品信息
     * @return
     */
    @Override
    public List<SecKillSkuRedisTo> getCurrentSecKillSkus() {

        // 1、确定当前时间属于那个秒杀场次
        long time = System.currentTimeMillis();
        // 定义一段受保护的资源
        //try (Entry entry = SphU.entry("seckillSkus")) {
            //从Redis中查询到所有key以seckill:sessions开头的所有数据
            Set<String> keys = stringRedisTemplate.keys(SESSION_CACHE_PREFIX + "*");
            for (String key : keys) {
                // seckill:sessions:1593993600000_1593995400000
                String replace = key.replace("seckill:sessions:", "");
                String[] split = replace.split("_");
                //获取存入Redis商品的开始、结束时间
                long start = Long.parseLong(split[0]);
                long end = Long.parseLong(split[1]);

                if (time >= start && time <= end) {
                    // 2、获取这个秒杀场次的所有商品信息
                    List<String> range = stringRedisTemplate.opsForList().range(key, 0, 100);
                    BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);

                    List<String> list = hashOps.multiGet(range);
                    if (list != null) {
                         List<SecKillSkuRedisTo> collect = list.stream().map(item -> {
                            SecKillSkuRedisTo redisTo = JSON.parseObject(item, SecKillSkuRedisTo.class);
						    //redisTo.setRandomCode(null); 当前秒杀开始就需要随机码
                            return redisTo;
                        }).collect(Collectors.toList());
                        return collect;
                    }
                    break;
                }
            }
       /* } catch (BlockException e) {
            log.warn("资源被限流：" + e.getMessage());
        }*/
        return null;
    }

    /**
     * 根据skuId查询商品是否参加秒杀活动
     * @param skuId
     * @return
     */
    @Override
    public SecKillSkuRedisTo getSkuSecKilInfo(Long skuId) {

        //1、找到所有需要参与秒杀的商品的key
        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);

        Set<String> keys = hashOps.keys();
        if (keys != null && keys.size() > 0) {
            //正则匹配
            String regx = "\\d_" + skuId;
            for (String key : keys) {
                if (Pattern.matches(regx, key)) {
                    //从Redis中取出数据来
                    String json = hashOps.get(key);
                    SecKillSkuRedisTo to = JSON.parseObject(json, SecKillSkuRedisTo.class);

                    //处理随机码
                    long current = System.currentTimeMillis();
                    if (current <= to.getStartTime() || current >= to.getEndTime()) {
                        to.setRandomCode(null);
                    }
                    return to;
                }
            }
        }
        return null;
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
                            item.getPromotionSessionId() + "_" + item.getSkuId().toString()).collect(Collectors.toList());
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
                    String token = UUID.randomUUID().toString().replace("_", "");
                    String redisKey = seckillSkuVo.getPromotionSessionId().toString() + "_" + seckillSkuVo.getSkuId().toString();
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
                        operations.put(seckillSkuVo.getPromotionSessionId().toString() + "_" + seckillSkuVo.getSkuId().toString(), seckillValue);

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
