package com.lee.jxmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 13:31:06
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

