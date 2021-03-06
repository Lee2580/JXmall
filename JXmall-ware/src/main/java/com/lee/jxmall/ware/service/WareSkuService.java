package com.lee.jxmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.to.OrderTo;
import com.lee.common.to.mq.StockLockedTo;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.ware.entity.WareSkuEntity;
import com.lee.jxmall.ware.vo.SkuHasStockVo;
import com.lee.jxmall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * εεεΊε­
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 15:08:13
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    Boolean orderLockStock(WareSkuLockVo lockVo);

    void unlockStock(StockLockedTo to);

    void unLockStock(Long skuId,Long wareId,Integer num,Long taskDetailId);

    void unlockStock(OrderTo orderTo);
}

