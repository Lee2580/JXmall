package com.lee.jxmall.ware.feign;

import com.lee.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient
public interface MemberFeignService {

    @RequestMapping("member/memberreceiveaddress/info/{id}")
    R addrInfo(@PathVariable("id") Long id);
}
