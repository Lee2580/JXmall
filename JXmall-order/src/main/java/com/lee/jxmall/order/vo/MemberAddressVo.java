package com.lee.jxmall.order.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 会员收获地址信息
 */
@Data
public class MemberAddressVo {

    @TableId
    private Long id;
    /**
     * member_id
     */
    private Long memberId;
    /**
     * 收货人姓名
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮政编码
     */
    private String postCode;
    /**
     * 省份/直辖市
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区
     */
    private String region;
    /**
     * 详细地址(街道)
     */
    private String detailAddress;
    /**
     * 省市区代码
     */
    private String areaCode;
    /**
     * 是否默认
     */
    private Integer defaultStatus;
}
