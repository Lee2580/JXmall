package com.lee.jxmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 14:58:53
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

