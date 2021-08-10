package com.lee.common.exception;

import lombok.Getter;
import lombok.Setter;

public class NoStockException extends RuntimeException{

    @Getter
    @Setter
    private Long skuId;

    public NoStockException(Long skuId){
        super(skuId+" 库存不足，请在补仓后重新下单");
    }

    public NoStockException(String msg) {
        super(msg);
    }
}
