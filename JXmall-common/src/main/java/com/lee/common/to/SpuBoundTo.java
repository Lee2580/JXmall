package com.lee.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 *  两个微服务之间传递的数据传输对象
 *  spu的积分信息
 */
@Data
public class SpuBoundTo {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
