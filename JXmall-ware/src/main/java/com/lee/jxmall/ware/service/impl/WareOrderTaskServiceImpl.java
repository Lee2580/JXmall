package com.lee.jxmall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.ware.dao.WareOrderTaskDao;
import com.lee.jxmall.ware.entity.WareOrderTaskEntity;
import com.lee.jxmall.ware.service.WareOrderTaskService;


@Service("wareOrderTaskService")
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskDao, WareOrderTaskEntity> implements WareOrderTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareOrderTaskEntity> page = this.page(
                new Query<WareOrderTaskEntity>().getPage(params),
                new QueryWrapper<WareOrderTaskEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询库存工作单
     * @param orderSn
     * @return
     */
    @Override
    public WareOrderTaskEntity getOrderTaskByOrderSn(String orderSn) {

        WareOrderTaskEntity order_sn = this.getOne(new QueryWrapper<WareOrderTaskEntity>().eq("order_sn", orderSn));
        return order_sn;
    }

}
