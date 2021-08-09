package com.lee.jxmall.order.to;

import com.lee.jxmall.order.entity.OrderEntity;
import com.lee.jxmall.order.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提交订单接口
 */
@Data
public class OrderCreateTo {
    /**
     * 订单
     */
    private OrderEntity order;
    /**
     * 订单项
     */
    private List<OrderItemEntity> orderItems;
    /**
     * 订单计算的应付价格
     */
    private BigDecimal payPrice;
    /**
     * 运费
     */
    private BigDecimal fare;
}
