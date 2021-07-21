package com.lee.jxmall.product.dao;

import com.lee.jxmall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 13:31:06
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
