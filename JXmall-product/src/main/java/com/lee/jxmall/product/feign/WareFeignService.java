package com.lee.jxmall.product.feign;

import com.lee.common.utils.R;
import com.lee.common.to.SkuHasStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("jxmall-ware")
public interface WareFeignService {

    /**
     * 1、R设计的时候可以加上泛型
     * 2、直接返回想要的结果
     * 3、自己封装解析结果
     * @param SkuIds
     * @return
     */
    @PostMapping("ware/waresku/hasStock")
    R getSkuHasStock(@RequestBody List<Long> SkuIds);

}
