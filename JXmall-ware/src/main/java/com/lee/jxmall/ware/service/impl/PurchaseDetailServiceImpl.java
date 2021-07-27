package com.lee.jxmall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.ware.dao.PurchaseDetailDao;
import com.lee.jxmall.ware.entity.PurchaseDetailEntity;
import com.lee.jxmall.ware.service.PurchaseDetailService;
import org.springframework.util.ObjectUtils;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    /**
     * 采购需求查询
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        /**
         *  key: '华为',//检索关键字
         *  status: 0,//状态
         *  wareId: 1,//仓库id
         */
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if(!ObjectUtils.isEmpty(key)){
            //需要拼接
            queryWrapper.and((wrapper)->{
                wrapper.eq("purchase_id",key).or().eq("sku_id",key);
            });
        }
        String status = (String) params.get("status");
        if(!ObjectUtils.isEmpty(status)){
            queryWrapper.eq("status", status);
        }
        String wareId = (String) params.get("wareId");
        if(!ObjectUtils.isEmpty(wareId)){
            queryWrapper.eq("ware_id",wareId);
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),queryWrapper);

        return new PageUtils(page);

    }

}
