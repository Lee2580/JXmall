package com.lee.jxmall.ware.service.impl;

import com.lee.common.utils.R;
import com.lee.jxmall.ware.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.ware.dao.WareSkuDao;
import com.lee.jxmall.ware.entity.WareSkuEntity;
import com.lee.jxmall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    WareSkuDao wareSkuDao;

    @Autowired
    ProductFeignService productFeignService;

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

    /**
     * 添加库存
     * @param skuId
     * @param wareId
     * @param skuNum
     * @return
     */
    @Transactional
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {

        //1、判断如果没有这个库存记录  新增
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().
                eq("sku_id", skuId).eq("ware_id", wareId));
        if(wareSkuEntities == null || wareSkuEntities.size() ==0 ){
            //新增
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);

            //远程查询SKU的name，若失败无需回滚
            //1、自己catch异常
            //TODO 还可以用什么办法让异常出现以后不回滚？高级
            try {
                R info = productFeignService.info(skuId);
                //成功
                if(info.getCode() == 0){
                    Map<String,Object> data=(Map<String,Object>) info.get("skuInfo");
                    wareSkuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {
                System.out.println("com.lee.mall.ware.service.impl.WareSkuServiceImpl：远程调用出错");
            }
            wareSkuDao.insert(wareSkuEntity);
        }else{
            //插入
            wareSkuDao.addStock(skuId,wareId,skuNum);
        }

    }

}
