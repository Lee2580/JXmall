package com.lee.jxmall.product.service.impl;

import com.lee.jxmall.product.vo.SkuItemSaleAttrVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.product.dao.SkuSaleAttrValueDao;
import com.lee.jxmall.product.entity.SkuSaleAttrValueEntity;
import com.lee.jxmall.product.service.SkuSaleAttrValueService;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取销售属性组合
     * @param spuId
     * @return
     */
    @Override
    public List<SkuItemSaleAttrVo> getSaleAttrsBuSpuId(Long spuId) {

        SkuSaleAttrValueDao dao = this.baseMapper;
        List<SkuItemSaleAttrVo> vos = dao.getSaleAttrsBuSpuId(spuId);

        return vos;
    }


    @Override
    public List<String> getSkuSaleAttrValueAsStringList(Long skuId) {

        SkuSaleAttrValueDao dao = this.baseMapper;

        return dao.getSkuSaleAttrValueAsStringList(skuId);
    }

}
