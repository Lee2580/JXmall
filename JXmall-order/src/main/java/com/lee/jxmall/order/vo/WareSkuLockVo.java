package com.lee.jxmall.order.vo;

import lombok.Data;

import java.util.List;

/**
 * 锁库存的vo
 */
@Data
public class WareSkuLockVo {

    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 需要锁住的所有库存信息
     */
    private List<OrderItemVo> locks;
}
