package com.lee.jxmall.product.dao;

import com.lee.jxmall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.jxmall.product.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 13:31:06
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
