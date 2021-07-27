package com.lee.jxmall.product.vo;

import com.lee.jxmall.product.entity.AttrEntity;
import com.lee.jxmall.product.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrsVo extends AttrGroupEntity {

    /**
     * 封装整个实体信息
     */
    private List<AttrEntity> attrs;
}
