package com.lee.jxmall.product.feign;

import com.lee.common.utils.R;
import com.lee.jxmall.product.fallback.SecKillFeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "jxmall-seckill",fallback = SecKillFeignServiceFallBack.class)
public interface SecKillFeignService {

    @ResponseBody
    @GetMapping(value = "/sku/seckill/{skuId}")
    R getSkuSeckilInfo(@PathVariable("skuId") Long skuId);
}
