package com.lee.jxmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.coupon.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 14:24:35
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

