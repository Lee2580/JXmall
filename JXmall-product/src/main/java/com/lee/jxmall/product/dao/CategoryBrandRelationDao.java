package com.lee.jxmall.product.dao;

import com.lee.jxmall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 11:01:33
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catId")Long catId,@Param("name") String name);
}
