package com.lee.jxmall.product.feign;

import com.lee.common.to.SkuReductionTo;
import com.lee.common.to.SpuBoundTo;
import com.lee.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "jxmall-coupon")
public interface CouponFeignService {

    /**
     * 1、couponFeignService.saveSpuBounds(spuBoundTo)找到这个远程服务
     *      1）、@RequestBody 将这个对象转为json
     *      2）、找到gulimall-coupon服务，发送/coupon/spubounds/save请求
     *      3）、将1）中的json放在请求体位置，发送请求
     *      4）、对方服务收到请求，请求体里有json数据
     *      5）、(@RequestBody SpuBoundsEntity spuBounds) 将请求体中的json转换为SpuBoundsEntity
     *
     *  只要json数据模型是兼容的，双方服务无需用同一个to
     */
    @PostMapping("/coupon/spubounds/save")
    public R saveSpuBounds(@RequestBody SpuBoundTo spuBounds);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    public R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
