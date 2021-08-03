package com.lee.jxmall.search.feign;

import com.lee.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 远程调用商品服务
 */
@FeignClient("jxmall-product")
public interface ProductFeignService {

    @GetMapping("/product/attr/info/{attrId}")
    R getAttrsInfo(@PathVariable("attrId") Long attrId);

    @GetMapping("product/brand/infos")
    R brandsInfo(@RequestParam("brandIds") List<Long> brandIds);
}
