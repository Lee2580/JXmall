package com.lee.jxmall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.ware.dao.WareSkuDao;
import com.lee.jxmall.ware.entity.WareSkuEntity;
import com.lee.jxmall.ware.service.WareSkuService;
import org.springframework.util.ObjectUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    /**
     * 分页模糊查询库存
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if(!ObjectUtils.isEmpty(skuId)){
            //前端界面有sku_id选项，不再是关键字key (参数名)
            queryWrapper.eq("sku_id",skuId);
        }
        //前端界面有ware_id选项，
        String wareId = (String) params.get("wareId");
        if(!ObjectUtils.isEmpty(wareId)){
            queryWrapper.eq("ware_id",wareId);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),queryWrapper);

        return new PageUtils(page);

    }

}
