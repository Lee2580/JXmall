package com.lee.jxmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.product.entity.SpuInfoEntity;
import com.lee.jxmall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 11:01:33
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity infoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void up(Long spuId);

    SpuInfoEntity getSpuInfoBySkuId(Long skuId);
}

