package com.lee.jxmall.ware.vo;

import lombok.Data;

@Data
public class LockStockResult {

    private Long skuId;
    /**
     * 锁几件
     */
    private Integer num;
    /**
     * 是否锁定成功
     */
    private Boolean locked;
}
