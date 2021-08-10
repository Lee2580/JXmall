package com.lee.jxmall.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class SkuWareHasStock {

    private Long skuId;
    /**
     * 哪些仓库有库存
     */
    private List<Long> wareId;

    /**
     * 锁几件
     */
    private Integer num;
}
