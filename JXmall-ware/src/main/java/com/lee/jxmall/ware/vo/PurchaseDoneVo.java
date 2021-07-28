package com.lee.jxmall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PurchaseDoneVo {

    /**
     * 采购单id
     */
    @NotNull
    private Long id;

    /**
     * 采购项(需求)
     */
    private List<PurchaseItemDoneVo> items;
}
