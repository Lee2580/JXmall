package com.lee.jxmall.ware.exception;

public class NoStockException extends RuntimeException{

    private Long skuId;

    public NoStockException(Long skuId){
        super(skuId+" 库存不足，请在补仓后重新下单");
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
