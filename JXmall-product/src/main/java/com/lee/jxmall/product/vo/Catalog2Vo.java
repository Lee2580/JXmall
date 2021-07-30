package com.lee.jxmall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 首页二级分类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catalog2Vo {

    private String id;

    private String name;
    /**
     * 一级父分类id
     */
    private String catalog1Id;
    /**
     * 三级子分类
     */
    private List<Catalog3Vo> catalog3List;
}
