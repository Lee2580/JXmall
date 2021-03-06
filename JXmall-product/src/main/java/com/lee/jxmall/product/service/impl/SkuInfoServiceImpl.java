package com.lee.jxmall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.lee.common.utils.R;
import com.lee.jxmall.product.entity.SkuImagesEntity;
import com.lee.jxmall.product.entity.SpuInfoDescEntity;
import com.lee.jxmall.product.feign.SecKillFeignService;
import com.lee.jxmall.product.service.*;
import com.lee.jxmall.product.vo.SecKillInfoVo;
import com.lee.jxmall.product.vo.SkuItemSaleAttrVo;
import com.lee.jxmall.product.vo.SkuItemVo;
import com.lee.jxmall.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.product.dao.SkuInfoDao;
import com.lee.jxmall.product.entity.SkuInfoEntity;
import org.springframework.util.ObjectUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    AttrGroupService attrGroupService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    SecKillFeignService secKillFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * ??????sku???????????????
     * @param skuInfoEntity
     */
    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    /**
     * sku????????????
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        QueryWrapper<SkuInfoEntity> queryWrapper = new QueryWrapper<>();
        /**
         * key:
         * catelogId: 0
         * brandId: 0
         * min: 0
         * max: 0
         */
        String key = (String) params.get("key");
        //??????????????????
        if(!ObjectUtils.isEmpty(key)){
            queryWrapper.and((wrapper)->{
                wrapper.eq("sku_id",key).or().like("sku_name",key);
            });
        }

        String catelogId = (String) params.get("catelogId");
        if(!ObjectUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)){

            queryWrapper.eq("catalog_id",catelogId);
        }

        String brandId = (String) params.get("brandId");
        if(!ObjectUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(catelogId)){
            queryWrapper.eq("brand_id",brandId);
        }

        String min = (String) params.get("min");
        if(!ObjectUtils.isEmpty(min)){
            //ge ????????????
            queryWrapper.ge("price",min);
        }

        String max = (String) params.get("max");

        if(!ObjectUtils.isEmpty(max)){
            try{
                BigDecimal bigDecimal = new BigDecimal(max);
                if(bigDecimal.compareTo(new BigDecimal("0"))==1){
                    //????????????
                    queryWrapper.le("price",max);
                }
            }catch (Exception e){

            }
        }
        //??????????????????
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    /**
     * ????????????id???sku??????
     * @param spuId
     * @return
     */
    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        return this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
    }

    /**
     * ????????????sku?????????
     * @param skuId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public SkuItemVo info(Long skuId) throws ExecutionException, InterruptedException {

        SkuItemVo skuItemVo = new SkuItemVo();
        /**
         * 1???2???6????????????
         */
        //1???sku??????????????????   pms_sku_info
        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {

            SkuInfoEntity info = getById(skuId);
            skuItemVo.setInfo(info);
            return info;
        }, threadPoolExecutor);
        /**
         * 3???4???5??????????????????????????????1??????
         */
        //3???spu?????????????????????
        CompletableFuture<Void> saleAttrFuture =infoFuture.thenAcceptAsync(res -> {

            List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrsBuSpuId(res.getSpuId());
            skuItemVo.setSaleAttr(saleAttrVos);
        },threadPoolExecutor);

        //4?????????spu???????????????????????????  pms_spu_info_desc
        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync(res -> {

            SpuInfoDescEntity spuInfo = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setDesc(spuInfo);
        }, threadPoolExecutor);

        //5?????????spu?????????????????????
        CompletableFuture<Void> baseAttrFuture = infoFuture.thenAcceptAsync(res -> {

            List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.
                    getAttrGroupWithAttrsBySpuId(res.getSpuId(), res.getCatalogId());
            skuItemVo.setGroupAttrs(attrGroupVos);
        }, threadPoolExecutor);

        //2???sku????????????     psm_sku_images
        CompletableFuture<Void> ImgageFuture = CompletableFuture.runAsync(() -> {

            List<SkuImagesEntity> images = skuImagesService.getImagesBySkuId(skuId);
            skuItemVo.setImages(images);
        }, threadPoolExecutor);

        // 6.????????????sku????????????????????????
        CompletableFuture<Void> secKillFuture = CompletableFuture.runAsync(() -> {
            R skuSeckillInfo = secKillFeignService.getSkuSeckilInfo(skuId);
            if (skuSeckillInfo.getCode() == 0) {
                SecKillInfoVo secKillInfoVo = skuSeckillInfo.getData(new TypeReference<SecKillInfoVo>() {
                });
                skuItemVo.setSecKillInfoVo(secKillInfoVo);
            }
        }, threadPoolExecutor);

        // ????????????????????????????????????
        //CompletableFuture.allOf(ImgageFuture,saleAttrFuture,descFuture,baseAttrFuture).get();
        CompletableFuture.allOf(ImgageFuture, saleAttrFuture, descFuture, baseAttrFuture, secKillFuture).get();

        return skuItemVo;
    }

}
