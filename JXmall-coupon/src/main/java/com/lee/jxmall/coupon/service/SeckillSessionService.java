package com.lee.jxmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.coupon.entity.SeckillSessionEntity;

import java.util.List;
import java.util.Map;

/**
 * 秒杀活动场次
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 14:24:35
 */
public interface SeckillSessionService extends IService<SeckillSessionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SeckillSessionEntity> getLates3DaySession();
}

