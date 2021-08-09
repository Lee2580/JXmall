package com.lee.jxmall.order.vo;

import com.lee.jxmall.order.entity.OrderEntity;
import lombok.Data;

/**
 * 提交订单返回结果
 */
@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;

    /**
     * 错误状态码    0成功
     */
    private Integer code;
}
