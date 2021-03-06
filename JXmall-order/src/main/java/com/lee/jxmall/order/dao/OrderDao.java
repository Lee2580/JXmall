package com.lee.jxmall.order.dao;

import com.lee.jxmall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 14:58:53
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    /**
     * 修改订单状态
     * @param orderSn
     * @param code
     */
    void updateOrderStatus(@Param("orderSn") String orderSn, @Param("code") Integer code);
}
