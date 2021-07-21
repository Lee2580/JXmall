package com.lee.jxmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.order.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 14:58:53
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

