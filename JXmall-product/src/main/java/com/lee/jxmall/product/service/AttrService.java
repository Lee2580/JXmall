package com.lee.jxmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 13:31:06
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

