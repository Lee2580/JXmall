package com.lee.jxmall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FareVo {
    /**
     * 地址信息
     */
    private MemberAddressVo memberAddressVo;
    /**
     * 运费
     */
    private BigDecimal fare;
}
