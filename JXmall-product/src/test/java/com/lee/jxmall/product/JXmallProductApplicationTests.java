package com.lee.jxmall.product;

import cn.hutool.extra.template.engine.wit.WitTemplate;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.jxmall.product.entity.BrandEntity;
import com.lee.jxmall.product.service.BrandService;
import com.lee.jxmall.product.service.SkuInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class JXmallProductApplicationTests {

    @Autowired
    BrandService brandService;

    //测试添加
    @Test
    void contextLoads() {

        BrandEntity brandEntity = new BrandEntity();
        //名字
        brandEntity.setName("苹果");
        brandService.save(brandEntity);
        System.out.println("保存成功");

    }

    //测试修改
    @Test
    void contextLoads1() {

        BrandEntity brandEntity = new BrandEntity();

        brandEntity.setBrandId(6L);
        brandEntity.setDescript("iphone 13");
        brandService.updateById(brandEntity);
        System.out.println("修改成功");

    }

    //查询测试
    @Test
    void contextLoads2() {

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        for (BrandEntity b:list) {
            System.out.println(b);
        }

    }
}
