package com.lee.jxmall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.lee.common.exception.NoStockException;
import com.lee.common.to.MemberRespVo;
import com.lee.common.to.OrderTo;
import com.lee.common.to.mq.SecKillOrderTo;
import com.lee.common.utils.R;
import com.lee.jxmall.order.constant.OrderConstant;
import com.lee.jxmall.order.entity.OrderItemEntity;
import com.lee.jxmall.order.entity.PaymentInfoEntity;
import com.lee.jxmall.order.enume.OrderStatusEnum;
import com.lee.jxmall.order.feign.CartFeignService;
import com.lee.jxmall.order.feign.MemberFeignService;
import com.lee.jxmall.order.feign.ProductFeignService;
import com.lee.jxmall.order.feign.WmsFeignService;
import com.lee.jxmall.order.interceptor.LoginUserInterceptor;
import com.lee.jxmall.order.service.OrderItemService;
import com.lee.jxmall.order.service.PaymentInfoService;
import com.lee.jxmall.order.to.OrderCreateTo;
import com.lee.jxmall.order.vo.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.order.dao.OrderDao;
import com.lee.jxmall.order.entity.OrderEntity;
import com.lee.jxmall.order.service.OrderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    CartFeignService cartFeignService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    WmsFeignService wmsFeignService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    PaymentInfoService paymentInfoService;

    private ThreadLocal<OrderSubmitVo> confirmVoThreadLocal =new ThreadLocal<>();

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * ???????????????????????????????????????
     * @return
     */
    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {

        //??????OrderConfirmVo
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        //?????????????????????????????????
        MemberRespVo memberResponseVo = LoginUserInterceptor.loginUser.get();

        //?????????????????????????????????(??????Feign?????????????????????????????????)
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        //???????????????????????????
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            //????????????????????????????????????????????????
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //1??????????????????????????????????????????
            List<MemberAddressVo> address = memberFeignService.getAddress(memberResponseVo.getId());
            confirmVo.setAddress(address);
        }, threadPoolExecutor);

        //???????????????????????????
        CompletableFuture<Void> cartInfoFuture = CompletableFuture.runAsync(() -> {
            //???????????????????????????????????????????????????????????????ThreadLocal ?????????????????????
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //2????????????????????????????????????????????????
            List<OrderItemVo> currentCartItems = cartFeignService.getCurrentUserCartItems();
            confirmVo.setItems(currentCartItems);
            //feign???????????????????????????????????????????????????????????????
            //?????????????????????????????????????????????????????????????????????????????????????????????
        }, threadPoolExecutor).thenRunAsync(() -> {
            List<OrderItemVo> items = confirmVo.getItems();
            //?????????????????????id
            List<Long> skuIds = items.stream()
                    .map((itemVo -> itemVo.getSkuId()))
                    .collect(Collectors.toList());

            //3?????????????????????????????????
            R skuHasStock = wmsFeignService.getSkuHasStock(skuIds);
            List<SkuStockVo> skuStockVos = skuHasStock.getData("data", new TypeReference<List<SkuStockVo>>() {});

            if (skuStockVos != null && skuStockVos.size() > 0) {
                //???skuStockVos???????????????map
                Map<Long, Boolean> skuHasStockMap = skuStockVos.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                confirmVo.setStocks(skuHasStockMap);
            }
        },threadPoolExecutor);

        //4????????????????????????
        Integer integration = memberResponseVo.getIntegration();
        confirmVo.setIntegration(integration);

        //5??????????????????????????????

        //TODO 5???????????????(????????????????????????)
        //?????????????????????token????????????????????????????????????redis???
        String token = UUID.randomUUID().toString().replace("-", "");
        //????????????????????????????????????
        stringRedisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX +memberResponseVo.getId(),token,30, TimeUnit.MINUTES);
        confirmVo.setOrderToken(token);

        CompletableFuture.allOf(addressFuture,cartInfoFuture).get();

        return confirmVo;
    }

    /**
     * ???????????????????????????
     *      ??????????????????????????????+???????????????
     * @Transactional ???????????????????????????????????????????????????????????????????????????????????????????????????
     * @GlobalTransactional ?????????????????????????????????????????????????????????????????????
     * @param submitVo
     * @return
     */
    //@GlobalTransactional
    @Transactional
    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo) {

        // ??????????????????????????????
        confirmVoThreadLocal.set(submitVo);
        SubmitOrderResponseVo responseVo = new SubmitOrderResponseVo();
        // 0?????????
        responseVo.setCode(0);
        // ????????????????????????,?????????,?????????,?????????
        MemberRespVo memberResponseVo = LoginUserInterceptor.loginUser.get();

        // 1. ???????????? [?????????????????????] ?????? 0 or 1
        // 0 ?????????????????? 1 ????????????
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        String orderToken = submitVo.getOrderToken();

        // ?????????????????? ????????????
        Long result = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                Arrays.asList(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberResponseVo.getId()),
                orderToken);
        if (result == 0L) {
            // ??????????????????
            responseVo.setCode(1);
            return responseVo;
        } else {
            // ??????????????????
            // 1 .?????????????????????
            OrderCreateTo order = createOrder();
            // 2. ??????
            BigDecimal payAmount = order.getOrder().getPayAmount();
            BigDecimal voPayPrice = submitVo.getPayPrice();
            if (Math.abs(payAmount.subtract(voPayPrice).doubleValue()) < 0.01) {
                // ??????????????????

                // 4.????????????   ???????????????????????????
                WareSkuLockVo lockVo = new WareSkuLockVo();
                lockVo.setOrderSn(order.getOrder().getOrderSn());
                //????????????????????????
                List<OrderItemVo> locks = order.getOrderItems().stream().map(item -> {
                    OrderItemVo itemVo = new OrderItemVo();
                    // ?????????skuId ??????skuId??????????????????
                    itemVo.setSkuId(item.getSkuId());
                    itemVo.setCount(item.getSkuQuantity());
                    itemVo.setTitle(item.getSkuName());
                    return itemVo;
                }).collect(Collectors.toList());

                lockVo.setLocks(locks);
                // ???????????????
                R r = wmsFeignService.orderLockStock(lockVo);
                if (r.getCode() == 0) {
                    // ???????????? ????????????
                    responseVo.setOrder(order.getOrder());
                    // ????????????????????????????????????MQ
                    rabbitTemplate.convertAndSend("order-event-exchange",
                            "order.create.order", order.getOrder());
                    //3.????????????
                    saveOrder(order);
                    //int i = 10/0;
                } else {
                    // ????????????
                    String msg = (String) r.get("msg");
                    throw new NoStockException(msg);
                }
            } else {
                // ??????????????????
                responseVo.setCode(2);
            }
        }
        return responseVo;
    }

    /**
     * ??????????????????
     * @param orderSn
     * @return
     */
    @Override
    public OrderEntity getOrderByStatus(String orderSn) {
        OrderEntity order_sn = this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        return order_sn;
    }

    /**
     * ????????????
     * @param orderEntity
     */
    @Override
    public void closeOrder(OrderEntity orderEntity) {

        //?????????????????????????????????????????????????????????????????????????????????
        OrderEntity orderInfo = this.getOne(new QueryWrapper<OrderEntity>().
                eq("order_sn",orderEntity.getOrderSn()));

        if (orderInfo.getStatus().equals(OrderStatusEnum.CREATE_NEW.getCode())) {
            //???????????????????????????
            OrderEntity orderUpdate = new OrderEntity();
            orderUpdate.setId(orderInfo.getId());
            orderUpdate.setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(orderUpdate);

            // ???????????????MQ
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(orderInfo, orderTo);

            try {
                //TODO ?????????????????????????????????????????????????????????????????????(???????????????????????????????????????)?????????????????????????????????
                //TODO ???????????????????????????????????????????????????
                rabbitTemplate.convertAndSend("order-event-exchange",
                        "order.release.other", orderTo);
            } catch (Exception e) {
            }
        }
    }

    /**
     * ?????????????????????????????????
     * @param orderSn
     * @return
     */
    @Override
    public PayVo getOrderPay(String orderSn) {

        PayVo payVo = new PayVo();
        OrderEntity orderEntity = this.getOrderByStatus(orderSn);

        //???????????????????????????????????????????????????
        BigDecimal payAmount = orderEntity.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
        //????????????
        payVo.setTotal_amount(payAmount.toString());
        //?????????
        payVo.setOut_trade_no(orderEntity.getOrderSn());

        List<OrderItemEntity> orderItemEntities = orderItemService.list(
                new QueryWrapper<OrderItemEntity>().eq("order_sn", orderSn));
        OrderItemEntity orderItemEntity = orderItemEntities.get(0);
        //????????????
        payVo.setSubject(orderItemEntity.getSkuName());
        //????????????
        payVo.setBody(orderItemEntity.getSkuAttrsVals());
        return payVo;
    }

    /**
     * ???????????????????????????????????????
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {

        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();

        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                // ????????????????????????????????? [????????????]
                new QueryWrapper<OrderEntity>()
                        .eq("member_id",memberRespVo.getId()).orderByDesc("id")
        );

        //????????????????????????
        List<OrderEntity> orderEntityList = page.getRecords().stream().map(order -> {
            //??????????????????????????????????????????
            List<OrderItemEntity> orderItemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>()
                    .eq("order_sn", order.getOrderSn()));

            order.setItemEntities(orderItemEntities);
            return order;
        }).collect(Collectors.toList());

        page.setRecords(orderEntityList);

        return new PageUtils(page);
    }

    /**
     * ??????????????????????????????
     * @param asyncVo
     * @return
     */
    @Override
    public String handlePayResult(PayAsyncVo asyncVo) {

        //1???????????????????????????
        PaymentInfoEntity paymentInfo = new PaymentInfoEntity();
        paymentInfo.setOrderSn(asyncVo.getOut_trade_no());
        paymentInfo.setAlipayTradeNo(asyncVo.getTrade_no());
        paymentInfo.setTotalAmount(new BigDecimal(asyncVo.getBuyer_pay_amount()));
        paymentInfo.setSubject(asyncVo.getBody());
        paymentInfo.setPaymentStatus(asyncVo.getTrade_status());
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setCallbackTime(asyncVo.getNotify_time());

        //?????????????????????
        paymentInfoService.save(paymentInfo);

        //2?????????????????????
        //??????????????????
        String tradeStatus = asyncVo.getTrade_status();
        //????????????
        if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
            //???????????????
            String orderSn = asyncVo.getOut_trade_no();
            this.baseMapper.updateOrderStatus(orderSn,OrderStatusEnum.PAYED.getCode());
        }
        return "success";
    }

    /**
     * ???????????????
     * @param orderTo
     */
    @Override
    public void createSecKillOrder(SecKillOrderTo orderTo) {

        //TODO ??????????????????
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(orderTo.getOrderSn());
        orderEntity.setMemberId(orderTo.getMemberId());
        orderEntity.setCreateTime(new Date());
        BigDecimal totalPrice = orderTo.getSeckillPrice().multiply(BigDecimal.valueOf(orderTo.getNum()));
        orderEntity.setPayAmount(totalPrice);
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());

        //????????????
        this.save(orderEntity);

        //?????????????????????
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrderSn(orderTo.getOrderSn());
        orderItem.setRealAmount(totalPrice);
        //TODO ????????????SKU????????????????????????
        orderItem.setSkuQuantity(orderTo.getNum());

        //???????????????spu??????
        R spuInfo = productFeignService.getSpuInfoBySkuId(orderTo.getSkuId());
        SpuInfoVo spuInfoData = spuInfo.getData(new TypeReference<SpuInfoVo>() {
        });
        orderItem.setSpuId(spuInfoData.getId());
        orderItem.setSpuName(spuInfoData.getSpuName());
        orderItem.setSpuBrand(spuInfoData.getBrandId().toString());
        orderItem.setCategoryId(spuInfoData.getCatalogId());
        orderItem.setPromotionAmount(new BigDecimal("0.0"));
        orderItem.setCouponAmount(new BigDecimal("0.0"));
        orderItem.setIntegrationAmount(new BigDecimal("0.0"));

        //?????????????????????
        orderItemService.save(orderItem);
    }


    /**
     * ??????????????????
     * @param order
     */
    private void saveOrder(OrderCreateTo order) {

        //??????????????????
        OrderEntity orderEntity = order.getOrder();
        orderEntity.setModifyTime(new Date());
        orderEntity.setCreateTime(new Date());
        //????????????
        this.save(orderEntity);

        //?????????????????????
        List<OrderItemEntity> orderItems = order.getOrderItems();
        //???????????????????????????
        orderItemService.saveBatch(orderItems);
    }

    /**
     * ????????????
     * @return
     */
    private OrderCreateTo createOrder(){

        OrderCreateTo orderCreateTo = new OrderCreateTo();
        //???????????????
        String orderSn = IdWorker.getTimeId();
        // ????????????????????????????????????
        OrderEntity orderEntity = buildOrder(orderSn);

        //2????????????????????????????????????????????????
        List<OrderItemEntity> orderItemEntities = builderOrderItems(orderSn);

        //3?????????(??????????????????????????????)
        computePrice(orderEntity, orderItemEntities);

        orderCreateTo.setOrder(orderEntity);
        orderCreateTo.setOrderItems(orderItemEntities);

        return orderCreateTo;
    }

    /**
     * ????????????
     * @param orderEntity
     * @param orderItemEntities
     */
    private void computePrice(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities) {

        //??????
        BigDecimal totalPrice = new BigDecimal("0.0");
        //?????????
        BigDecimal coupon = new BigDecimal("0.0");
        BigDecimal intergration = new BigDecimal("0.0");
        BigDecimal promotion = new BigDecimal("0.0");

        //??????????????????
        BigDecimal gift = new BigDecimal("0.0");
        BigDecimal growth = new BigDecimal("0.0");

        //??????????????????????????????????????????????????????
        for (OrderItemEntity orderItem : orderItemEntities) {
            //??????????????????
            coupon = coupon.add(orderItem.getCouponAmount());
            promotion = promotion.add(orderItem.getPromotionAmount());
            intergration = intergration.add(orderItem.getIntegrationAmount());

            //??????
            totalPrice = totalPrice.add(orderItem.getRealAmount());

            //??????????????????????????????
            gift.add(new BigDecimal(orderItem.getGiftIntegration().toString()));
            growth.add(new BigDecimal(orderItem.getGiftGrowth().toString()));

        }

        //1????????????????????????
        orderEntity.setTotalAmount(totalPrice);
        //??????????????????(??????+??????)
        orderEntity.setPayAmount(totalPrice.add(orderEntity.getFreightAmount()));
        orderEntity.setCouponAmount(coupon);
        orderEntity.setPromotionAmount(promotion);
        orderEntity.setIntegrationAmount(intergration);

        //???????????????????????????
        orderEntity.setIntegration(gift.intValue());
        orderEntity.setGrowth(growth.intValue());

        //??????????????????(0-????????????1-?????????)
        orderEntity.setDeleteStatus(OrderStatusEnum.CREATE_NEW.getCode());

    }

    /**
     * ??????????????????
     * @param orderSn
     * @return
     */
    private OrderEntity buildOrder(String orderSn) {

        //??????????????????????????????
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setMemberId(memberRespVo.getId());
        orderEntity.setOrderSn(orderSn);
        orderEntity.setMemberUsername(memberRespVo.getUsername());

        OrderSubmitVo orderSubmitVo = confirmVoThreadLocal.get();

        //???????????????????????????????????????
        R fareAddressVo = wmsFeignService.getFare(orderSubmitVo.getAddrId());
        FareVo fareResp = fareAddressVo.getData("data", new TypeReference<FareVo>() {});

        //?????????????????????
        BigDecimal fare = fareResp.getFare();
        orderEntity.setFreightAmount(fare);

        //???????????????????????????
        MemberAddressVo address = fareResp.getMemberAddressVo();
        //?????????????????????
        orderEntity.setReceiverName(address.getName());
        orderEntity.setReceiverPhone(address.getPhone());
        orderEntity.setReceiverPostCode(address.getPostCode());
        orderEntity.setReceiverProvince(address.getProvince());
        orderEntity.setReceiverCity(address.getCity());
        orderEntity.setReceiverRegion(address.getRegion());
        orderEntity.setReceiverDetailAddress(address.getDetailAddress());

        //?????????????????????????????????
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        orderEntity.setAutoConfirmDay(7);
        orderEntity.setConfirmStatus(0);
        return orderEntity;
    }

    /**
     * ????????????????????????
     * @param orderSn
     * @return
     */
    public List<OrderItemEntity> builderOrderItems(String orderSn) {

        List<OrderItemEntity> orderItemEntityList = new ArrayList<>();

        //????????????????????????????????????
        List<OrderItemVo> currentCartItems = cartFeignService.getCurrentUserCartItems();
        if (currentCartItems != null && currentCartItems.size() > 0) {

            orderItemEntityList = currentCartItems.stream().map((items) -> {
                //?????????????????????
                OrderItemEntity orderItemEntity = builderOrderItem(items);
                orderItemEntity.setOrderSn(orderSn);

                return orderItemEntity;
            }).collect(Collectors.toList());
        }

        return orderItemEntityList;
    }

    /**
     * ???????????????????????????
     * @param items
     * @return
     */
    private OrderItemEntity builderOrderItem(OrderItemVo items) {

        OrderItemEntity orderItemEntity = new OrderItemEntity();

        //1????????????spu??????
        Long skuId = items.getSkuId();
        //??????spu?????????
        R spuInfo = productFeignService.getSpuInfoBySkuId(skuId);
        SpuInfoVo spuInfoData = spuInfo.getData("data", new TypeReference<SpuInfoVo>() {
        });
        orderItemEntity.setSpuId(spuInfoData.getId());
        orderItemEntity.setSpuName(spuInfoData.getSpuName());
        orderItemEntity.setSpuBrand(spuInfoData.getBrandId().toString());
        orderItemEntity.setCategoryId(spuInfoData.getCatalogId());

        //2????????????sku??????
        orderItemEntity.setSkuId(skuId);
        orderItemEntity.setSkuName(items.getTitle());
        orderItemEntity.setSkuPic(items.getImage());
        orderItemEntity.setSkuPrice(items.getPrice());
        orderItemEntity.setSkuQuantity(items.getCount());

        //??????StringUtils.collectionToDelimitedString???list???????????????String
        String skuAttr = StringUtils.collectionToDelimitedString(items.getSkuAttr(), ";");
        orderItemEntity.setSkuAttrsVals(skuAttr);

        //3????????????????????????

        //4????????????????????????
        orderItemEntity.setGiftGrowth(items.getPrice().multiply(new BigDecimal(items.getCount())).intValue());
        orderItemEntity.setGiftIntegration(items.getPrice().multiply(new BigDecimal(items.getCount())).intValue());

        //5???????????????????????????
        orderItemEntity.setPromotionAmount(BigDecimal.ZERO);
        orderItemEntity.setCouponAmount(BigDecimal.ZERO);
        orderItemEntity.setIntegrationAmount(BigDecimal.ZERO);

        //??????????????????????????????.?????? - ??????????????????
        //???????????????
        BigDecimal origin = orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity().toString()));
        //??????????????????????????????????????????
        BigDecimal subtract = origin.subtract(orderItemEntity.getCouponAmount())
                .subtract(orderItemEntity.getPromotionAmount())
                .subtract(orderItemEntity.getIntegrationAmount());
        orderItemEntity.setRealAmount(subtract);

        return orderItemEntity;
    }

}
