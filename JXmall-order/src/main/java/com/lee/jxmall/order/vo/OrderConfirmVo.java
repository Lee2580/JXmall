package com.lee.jxmall.order.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单需要的数据
 */
public class OrderConfirmVo {

    /**
     * 收获地址列表
     */
    @Setter
    @Getter
    List<MemberAddressVo> address;

    /**
     * 所有选中的购物项
     */
    @Setter
    @Getter
    List<OrderItemVo> items;

    /**
     * 发票记录
     **/

    /**
     * 优惠券（会员积分）
     */
    @Getter
    @Setter
    private Integer integration;

    /**
     * 订单防重令牌
     *      防止重复提交
     */
    @Setter
    @Getter
    private String orderToken;

    /**
     * 表示库存
     */
    @Setter
    @Getter
    Map<Long, Boolean> stocks;

    /**
     * 获取商品总价格
     */
    public BigDecimal getTotal() {
        BigDecimal sum = new BigDecimal("0");
        if (items != null) {
            for (OrderItemVo item : items) {
                sum = sum.add(item.getPrice().multiply(new BigDecimal(item.getCount().toString())));
            }
        }
        return sum;
    }

    /**
     * 应付的价格
     */
    //BigDecimal payPrice;
    public BigDecimal getPayPrice() {
        return getTotal();
    }

    /**
     * 商品的总件数
     * @return
     */
    public Integer getCount() {
        Integer i = 0;
        if (items != null) {
            for (OrderItemVo item : items) {
                i += item.getCount();
            }
        }
        return i;
    }

    /**
     * 发票信息...
     */
}
