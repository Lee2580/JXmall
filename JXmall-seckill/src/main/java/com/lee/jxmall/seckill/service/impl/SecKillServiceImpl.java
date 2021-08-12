package com.lee.jxmall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.lee.common.to.MemberRespVo;
import com.lee.common.to.mq.SecKillOrderTo;
import com.lee.common.utils.R;
import com.lee.jxmall.seckill.feign.CouponFeignService;
import com.lee.jxmall.seckill.feign.ProductFeignService;
import com.lee.jxmall.seckill.interceptor.LoginUserInterceptor;
import com.lee.jxmall.seckill.service.SecKillService;
import com.lee.jxmall.seckill.to.SecKillSkuRedisTo;
import com.lee.jxmall.seckill.vo.SecKillSessionWithSkusVo;
import com.lee.jxmall.seckill.vo.SkuInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
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

    @Autowired
    RabbitTemplate rabbitTemplate;

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
     * 当前商品进行秒杀
     * @param killId
     * @param key
     * @param num
     * @return
     */
    @Override
    public String kill(String killId, String key, Integer num) throws InterruptedException {

        //获取当前用户的信息
        MemberRespVo user = LoginUserInterceptor.loginUser.get();

        //1、获取当前秒杀商品的详细信息   从Redis中获取
        BoundHashOperations<String, String, String> hashOps = stringRedisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);

        String json = hashOps.get(killId);
        if (ObjectUtils.isEmpty(json)) {
            return null;
        }
        //校验合法性
        SecKillSkuRedisTo redisTo = JSON.parseObject(json, SecKillSkuRedisTo.class);
        Long startTime = redisTo.getStartTime();
        Long endTime = redisTo.getEndTime();
        long currentTime = new Date().getTime();

        //判断当前这个秒杀请求是否在活动时间区间内( 效验时间的合法性)
        if (currentTime >= startTime && currentTime <= endTime) {

            //2、效验随机码和商品id
            String randomCode = redisTo.getRandomCode();
            String skuId = redisTo.getPromotionSessionId() + "_" +redisTo.getSkuId();
            if (randomCode.equals(key) && killId.equals(skuId)) {

                //3、验证购物数量是否合理和库存量是否充足
                Integer seckillLimit = redisTo.getSeckillLimit();
                //获取信号量
                String seckillCount = stringRedisTemplate.opsForValue().get(SKU_STOCK_SEMAPHORE + randomCode);
                Integer count = Integer.valueOf(seckillCount);
                //判断信号量是否大于0,并且买的数量不能超过库存
                if (count > 0 && num <= seckillLimit && count > num ) {

                    //4、验证这个人是否已经买过了（幂等性处理）,如果秒杀成功，就去占位。userId_sessionId_skuId
                    //SETNX 原子性处理
                    String redisKey = user.getId() + "_" + skuId;
                    //设置自动过期    ttl=(活动结束时间-当前时间)
                    Long ttl = endTime - currentTime;
                    Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);

                    if (aBoolean) {
                        //占位成功说明从来没有买过,分布式锁(获取信号量-1)【分布式锁-1】
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomCode);
                        boolean semaphoreCount = semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
                        //秒杀成功，快速下单，保证Redis中还有商品库存
                        if (semaphoreCount) {
                            //创建订单号和订单信息发送给MQ
                            // 秒杀成功 快速下单 发送消息到 MQ 整个操作时间在 10ms 左右
                            String timeId = IdWorker.getTimeId();
                            SecKillOrderTo orderTo = new SecKillOrderTo();
                            orderTo.setOrderSn(timeId);
                            orderTo.setMemberId(user.getId());
                            orderTo.setNum(num);
                            orderTo.setPromotionSessionId(redisTo.getPromotionSessionId());
                            orderTo.setSkuId(redisTo.getSkuId());
                            orderTo.setSeckillPrice(redisTo.getSeckillPrice());
                            rabbitTemplate.convertAndSend("order-event-exchange","order.seckill.order",orderTo);

                            return timeId;
                        }
                    }
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
                    String token = UUID.randomUUID().toString().replace("-", "");
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
