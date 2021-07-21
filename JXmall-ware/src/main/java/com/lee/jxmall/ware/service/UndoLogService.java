package com.lee.jxmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.ware.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 15:08:13
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

