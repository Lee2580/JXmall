package com.lee.jxmall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 整个购物车
 *      需要计算的属性，都需要重写get方法，保证每次获取属性都会进行计算
 */
public class CartVo {

    private List<CartItemVo> items;

    /**
     * 商品的数量
     */
    private Integer countNum;

    /**
     * 商品的类型数量
     */
    private Integer countType;

    /**
     * 整个购物车的总价
     */
    private BigDecimal totalAmount;

    /**
     * 减免的价格
     */
    private BigDecimal reduce = new BigDecimal("0.00");

    /**
     * 计算商品的总量
     *
     * @return
     */
    public Integer getCountNum() {
        int count = 0;
        if (this.items != null && this.items.size() > 0) {
            for (CartItemVo item : this.items) {
                count += item.getCount();
            }
        }
        return count;
    }

    /**
     * 计算商品的类型数量
     * @return
     */
    public Integer getCountType() {
        int count = 0;
        if (this.items != null && this.items.size() > 0) {
            for (CartItemVo item : this.items) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * 计算商品的总价
     * @return
     */
    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0");
        if (this.items != null && this.items.size() > 0) {
            for (CartItemVo item : this.items) {
                if (item.getCheck()) {
                    BigDecimal totalPrice = item.getTotalPrice();
                    amount = amount.add(totalPrice);
                }
            }
        }
        //减去优惠后的总价
        return amount.subtract(this.getReduce());
    }

    public List<CartItemVo> getItems() {
        return items;
    }

    public void setItems(List<CartItemVo> items) {
        this.items = items;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
