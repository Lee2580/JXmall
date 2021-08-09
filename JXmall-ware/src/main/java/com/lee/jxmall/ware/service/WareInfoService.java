package com.lee.jxmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.common.utils.PageUtils;
import com.lee.jxmall.ware.entity.WareInfoEntity;
import com.lee.jxmall.ware.vo.FareVo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 仓库信息
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 15:08:13
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    FareVo getFare(Long addrId);
}

