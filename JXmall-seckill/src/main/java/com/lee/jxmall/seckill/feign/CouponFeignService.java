package com.lee.jxmall.seckill.feign;

import com.lee.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("jxmall-coupon")
public interface CouponFeignService {

    @GetMapping("/coupon/seckillsession/lates3DaySession")
    R getLates3DaySession();
}
