package com.lee.jxmall.product.fallback;

import com.lee.common.exception.BizCodeEnum;
import com.lee.common.utils.R;
import com.lee.jxmall.product.feign.SecKillFeignService;
import org.springframework.stereotype.Component;

@Component
public class SecKillFeignServiceFallBack implements SecKillFeignService {

    @Override
    public R getSkuSeckilInfo(Long skuId) {
        //System.out.println("触发熔断");
        return R.error(BizCodeEnum.TOO_MANY_REQUEST.getCode(), BizCodeEnum.TOO_MANY_REQUEST.getMsg());
    }
}
