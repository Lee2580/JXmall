package com.lee.jxmall.product.vo;

import com.lee.jxmall.product.entity.SkuImagesEntity;
import com.lee.jxmall.product.entity.SkuInfoEntity;
import com.lee.jxmall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

/**
 * 查询商品详情VO
 */
@Data
public class SkuItemVo {

    /**
     * sku基本信息
     */
    SkuInfoEntity info;

    /**
     * 有无货
     */
    boolean hasStock = true;

    /**
     * sku图片信息
     */
    List<SkuImagesEntity> images;

    /**
     * 销售属性组合
     */
    List<SkuItemSaleAttrVo> saleAttr;

    /**
     * spu介绍
     */
    SpuInfoDescEntity desc;

    /**
     * spu参数规格信息
     */
    List<SpuItemAttrGroupVo> groupAttrs;

    /**
     * 秒杀信息
     */
    SecKillInfoVo secKillInfoVo;

}
