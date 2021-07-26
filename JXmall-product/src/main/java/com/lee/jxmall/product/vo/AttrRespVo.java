package com.lee.jxmall.product.vo;

import lombok.Data;

/**
 * 响应数据
 */
@Data
public class AttrRespVo extends AttrVo{

    //所属分类名字
    private String catelogName;
    //所属分组名字
    private String groupName;

    private Long[] catelogPath;
}
