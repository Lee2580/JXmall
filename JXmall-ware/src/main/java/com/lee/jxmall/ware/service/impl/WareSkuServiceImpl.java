package com.lee.jxmall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.lee.common.exception.NoStockException;
import com.lee.common.to.OrderTo;
import com.lee.common.to.mq.StockDetailTo;
import com.lee.common.to.mq.StockLockedTo;
import com.lee.common.utils.R;
import com.lee.jxmall.ware.entity.WareOrderTaskDetailEntity;
import com.lee.jxmall.ware.entity.WareOrderTaskEntity;
import com.lee.jxmall.ware.feign.OrderFeignService;
import com.lee.jxmall.ware.feign.ProductFeignService;
import com.lee.jxmall.ware.service.WareOrderTaskDetailService;
import com.lee.jxmall.ware.service.WareOrderTaskService;
import com.lee.jxmall.ware.vo.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.ware.dao.WareSkuDao;
import com.lee.jxmall.ware.entity.WareSkuEntity;
import com.lee.jxmall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    WareSkuDao wareSkuDao;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    WareOrderTaskService wareOrderTaskService;

    @Autowired
    WareOrderTaskDetailService wareOrderTaskDetailService;

    @Autowired
    OrderFeignService orderFeignService;

    /**
     * 分页模糊查询库存
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if(!ObjectUtils.isEmpty(skuId)){
            //前端界面有sku_id选项，不再是关键字key (参数名)
            queryWrapper.eq("sku_id",skuId);
        }
        //前端界面有ware_id选项，
        String wareId = (String) params.get("wareId");
        if(!ObjectUtils.isEmpty(wareId)){
            queryWrapper.eq("ware_id",wareId);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),queryWrapper);

        return new PageUtils(page);

    }

    /**
     * 添加库存
     * @param skuId
     * @param wareId
     * @param skuNum
     * @return
     */
    @Transactional
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {

        //1、判断如果没有这个库存记录  新增
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().
                eq("sku_id", skuId).eq("ware_id", wareId));
        if(wareSkuEntities == null || wareSkuEntities.size() ==0 ){
            //新增
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);

            //远程查询SKU的name，若失败无需回滚
            //1、自己catch异常
            //TODO 还可以用什么办法让异常出现以后不回滚？高级
            try {
                R info = productFeignService.info(skuId);
                //成功
                if(info.getCode() == 0){
                    Map<String,Object> data=(Map<String,Object>) info.get("skuInfo");
                    wareSkuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {
                System.out.println("com.lee.mall.ware.service.impl.WareSkuServiceImpl：远程调用出错");
            }
            wareSkuDao.insert(wareSkuEntity);
        }else{
            //插入
            wareSkuDao.addStock(skuId,wareId,skuNum);
        }

    }

    /**
     * 检查商品的库存
     * @param skuIds
     * @return
     */
    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> collect= skuIds.stream().map(skuId->{
            SkuHasStockVo vo = new SkuHasStockVo();

            //查询当前sku的总库存量
            Long count = baseMapper.getSkuStock(skuId);

            vo.setSkuId(skuId);
            vo.setHasStock(count==null?false:count>0);
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 为某个订单锁定库存
     *      默认只要是运行时异常都会回滚
     *
     * 库存解锁场景
     *  1、下订单成功，订单过期没有支付被系统自动取消、被用户手动取消，都要解锁库存
     *  2、下订单成功，库存锁定成功，接下来的业务调用失败，导致订单回滚
     *      之前锁定的库存就要自动解锁
     * @param lockVo
     * @return
     */
    @Transactional(rollbackFor = NoStockException.class)
    @Override
    public Boolean orderLockStock(WareSkuLockVo lockVo) {

        /**
         * 保存库存工作单详情信息
         *      用来追溯
         * 如果没有库存，就不会发送消息给mq
         * 【不会进入save(WareOrderTaskDetailEntity)逻辑，也不会发送消息给mq，也不会锁定库存，也不会监听到解锁服务】
         */
        //保存库存工作单详情
        WareOrderTaskEntity wareOrderTaskEntity = new WareOrderTaskEntity();
        wareOrderTaskEntity.setOrderSn(lockVo.getOrderSn());
        wareOrderTaskEntity.setCreateTime(new Date());
        wareOrderTaskService.save(wareOrderTaskEntity);

        //1、按照下单的收货地址，找到一个就近仓库，锁定库存
        //2、找到每个商品在哪个仓库都有库存
        List<OrderItemVo> locks = lockVo.getLocks();

        List<SkuWareHasStock> collect = locks.stream().map((item) -> {
            SkuWareHasStock stock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            stock.setSkuId(skuId);
            stock.setNum(item.getCount());

            //查询这个商品在哪个仓库有库存 stock-锁定num > 0
            List<Long> wareIdList = wareSkuDao.listWareIdHasSkuStock(skuId);
            stock.setWareId(wareIdList);

            return stock;
        }).collect(Collectors.toList());

        //2、锁定库存
        for (SkuWareHasStock hasStock : collect) {
            boolean skuStocked = false;
            Long skuId = hasStock.getSkuId();
            List<Long> wareIds = hasStock.getWareId();

            if (CollectionUtils.isEmpty(wareIds)) {
                //没有任何仓库有这个商品的库存
                throw new NoStockException(skuId);
            }

            //1、如果每一个商品都锁定成功，将当前商品锁定了几件的工作单记录发给MQ
            //2、锁定失败。前面保存的工作单信息都回滚了。发送出去的消息，即使要解锁库存，由于在数据库查不到指定的id，所有就不用解锁
            for (Long wareId : wareIds) {
                //锁定库存，根据几行受影响判断是否成功，成功就返回1，失败就返回0
                Long count = wareSkuDao.lockSkuStock(skuId,wareId,hasStock.getNum());
                // count==1表示锁定成功
                if (count == 1) {

                    WareOrderTaskDetailEntity taskDetailEntity = new WareOrderTaskDetailEntity(
                            null, skuId, "", hasStock.getNum(),
                            wareOrderTaskEntity.getId(), wareId, 1);

                    wareOrderTaskDetailService.save(taskDetailEntity);

                    //TODO 告诉MQ库存锁定成功
                    StockLockedTo lockedTo = new StockLockedTo();
                    lockedTo.setId(wareOrderTaskEntity.getId());
                    StockDetailTo detailTo = new StockDetailTo();
                    BeanUtils.copyProperties(taskDetailEntity,detailTo);
                    // 这里直接存entity。但是应该存id更好，数据最好来自DB
                    lockedTo.setDetailTo(detailTo);
                    rabbitTemplate.convertAndSend("stock-event-exchange","stock.locked",lockedTo);
                    //锁住了
                    skuStocked = true;
                    // 锁定成功返回
                    break;
                } else {
                    //当前仓库锁失败，重试下一个仓库
                }
            }
            if (skuStocked == false) {
                //当前商品所有仓库都没有锁住
                throw new NoStockException(skuId);
            }
        }
        //3、肯定全部都是锁定成功的
        return true;
    }

    /**
     * 解锁库存
     *  只要解锁库存的消息失败，一定要告诉服务解锁失败
     */
    @Override
    public void unlockStock(StockLockedTo to) {

        //库存工作单的id
        StockDetailTo detail = to.getDetailTo();
        Long detailId = detail.getId();

        /**
         * 解锁
         * 1、查询数据库关于这个订单锁定库存信息
         *   有：证明库存锁定成功了
         *      解锁：订单状况
         *          1、没有这个订单，必须解锁库存
         *          2、有这个订单，不一定解锁库存
         *              订单状态：已取消：解锁库存
         *                      已支付：不能解锁库存
         *   没有：库存锁定失败，库存回滚了，无序解锁
         */
        WareOrderTaskDetailEntity taskDetailEntity = wareOrderTaskDetailService.getById(detailId);
        if (taskDetailEntity != null) {
            //进行解锁
            //查出wms_ware_order_task工作单的信息
            Long id = to.getId();
            WareOrderTaskEntity orderTaskEntity = wareOrderTaskService.getById(id);
            //获取订单号查询订单状态
            String orderSn = orderTaskEntity.getOrderSn();
            //远程查询订单信息
            R orderData = orderFeignService.getOrderStatus(orderSn);
            if (orderData.getCode() == 0) {
                //订单数据返回成功
                OrderVo order = orderData.getData("data", new TypeReference<OrderVo>() {});
                //判断订单状态是否已取消或者支付或者订单不存在
                // 1、订单不存在：解锁
                // 2、订单存在，且订单状态是取消状态：解锁
                if (order == null || order.getStatus() == 4) {
                    // 工作单状态必须是 已锁定 才可以解锁【因为解锁方法没有加事务】
                    if (taskDetailEntity.getLockStatus() == 1) {
                        unLockStock(detail.getSkuId(),detail.getWareId(),detail.getSkuNum(),detailId);
                    }
                }
            } else {
                //消息拒绝以后重新放在队列里面，让别人继续消费解锁
                //远程调用服务失败
                throw new RuntimeException("远程调用服务失败");
            }
        } else {
            //无需解锁【回滚状态】
        }
    }

    /**
     * 解锁库存的方法
     * @param skuId
     * @param wareId
     * @param num
     * @param taskDetailId
     */
    @Override
    public void unLockStock(Long skuId,Long wareId,Integer num,Long taskDetailId) {
        // 1、库存解锁
        wareSkuDao.unLockStock(skuId,wareId,num);

        // 2、更新工作单的状态  设为已解锁 2
        WareOrderTaskDetailEntity taskDetailEntity = new WareOrderTaskDetailEntity();
        taskDetailEntity.setId(taskDetailId);
        taskDetailEntity.setLockStatus(2);
        wareOrderTaskDetailService.updateById(taskDetailEntity);
    }

    /**
     * 防止订单服务卡顿，导致订单状态消息一直改不了，库存优先到期，查订单状态新建，什么都不处理
     *  导致卡顿的订单，永远都不能解锁库存
     * @param orderTo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlockStock(OrderTo orderTo) {

        String orderSn = orderTo.getOrderSn();
        //查一下最新的库存解锁状态，防止重复解锁库存
        WareOrderTaskEntity orderTaskEntity = wareOrderTaskService.getOrderTaskByOrderSn(orderSn);
        Long id = orderTaskEntity.getId();

        //按照工作单的id找到所有 没有解锁的库存，进行解锁
        List<WareOrderTaskDetailEntity> list = wareOrderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>()
                .eq("task_id", id).eq("lock_status", 1));

        for (WareOrderTaskDetailEntity taskDetailEntity : list) {
            unLockStock(taskDetailEntity.getSkuId(),
                    taskDetailEntity.getWareId(),
                    taskDetailEntity.getSkuNum(),
                    taskDetailEntity.getId());
        }
    }

}
