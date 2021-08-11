package com.lee.jxmall.product.feign;

import com.lee.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("jxmall-seckill")
public interface SecKillFeignService {

    @ResponseBody
    @GetMapping(value = "/sku/seckill/{skuId}")
    R getSkuSeckilInfo(@PathVariable("skuId") Long skuId);
}
