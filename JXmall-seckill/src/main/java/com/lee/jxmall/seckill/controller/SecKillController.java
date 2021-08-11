package com.lee.jxmall.seckill.controller;

import com.lee.common.utils.R;
import com.lee.jxmall.seckill.service.SecKillService;
import com.lee.jxmall.seckill.to.SecKillSkuRedisTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    /**
     * 当前时间可以参与秒杀的商品信息
     */
    @ResponseBody
    @GetMapping( "/currentSeckillSkus")
    public R getCurrentSeckillSkus() {

        //获取到当前可以参加秒杀商品的信息
        List<SecKillSkuRedisTo> vos = secKillService.getCurrentSecKillSkus();

        return R.ok().setData(vos);
    }

    /**
     * 根据skuId查询商品是否参加秒杀活动
     */
    @ResponseBody
    @GetMapping(value = "/sku/seckill/{skuId}")
    public R getSkuSeckilInfo(@PathVariable("skuId") Long skuId) {

        SecKillSkuRedisTo to = secKillService.getSkuSecKilInfo(skuId);
        return R.ok().setData(to);
    }
}
