package com.lee.common.to;

import lombok.Data;

/**
 * skuId
 * 库存
 */
@Data
public class SkuHasStockVo {

    private Long skuId;
    private Boolean hasStock;
}
