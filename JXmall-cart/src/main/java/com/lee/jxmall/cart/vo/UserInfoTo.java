package com.lee.jxmall.cart.vo;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserInfoTo {

    private Long userId;
    private String userKey;

    /**
     * 存储用户名
     */
    private String username;

    /**
     * 有无临时用户
     */
    private boolean tempUser = false;
}
