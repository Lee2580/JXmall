package com.lee.jxmall.cart.service;

import com.lee.jxmall.cart.vo.CartItemVo;

import java.util.concurrent.ExecutionException;

public interface CartService {

    CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;
}
