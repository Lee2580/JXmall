package com.lee.jxmall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 首页三级分类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catalog3Vo {

    private String id;

    private String name;

    private String catalog2Id;
}
