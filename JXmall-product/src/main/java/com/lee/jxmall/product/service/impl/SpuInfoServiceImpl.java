package com.lee.jxmall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.lee.common.constant.ProductConstant;
import com.lee.common.to.SkuHasStockVo;
import com.lee.common.to.SkuReductionTo;
import com.lee.common.to.SpuBoundTo;
import com.lee.common.to.es.SkuEsModel;
import com.lee.common.utils.R;
import com.lee.jxmall.product.entity.*;
import com.lee.jxmall.product.feign.CouponFeignService;
import com.lee.jxmall.product.feign.SearchFeignService;
import com.lee.jxmall.product.feign.WareFeignService;
import com.lee.jxmall.product.service.*;
import com.lee.jxmall.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SpuImagesService imagesService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService attrValueService;
    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    private SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     *  保存发布商品的商品信息
     *  TODO 失败回滚待完善
     *
     *  业务逻辑：
     *  1、保存spu基本信息 pms_spu_info
     *  2、保存Spu的描述图片 pms_spu_info_desc
     *  3、保存spu的图片集 pms_spu_images
     *  4、保存spu的规格参数 pms_product_attr_value
     *  5、保存spu的积分信息；jxmall_sms->sms_spu_bounds
     *  6、保存当前spu对应的所有sku信息
     *      6.1）、sku的基本信息；pms_sku_info
     *      6.2）、sku的图片信息；pms_sku_image
     *      6.3）、sku的销售属性信息：pms_sku_sale_attr_value
     *      6.4）、sku的优惠、满减等信息；jxmall_sms->sms_sku_ladder\sms_sku_full_reduction\sms_member_price
     *
     * @GlobalTransactional 使用saeta分布式事务
     * @Transactional 开启事务
     * @param vo
     */
    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {

        //1、保存spu基本信息 pms_spu_info
        SpuInfoEntity infoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo,infoEntity);
        //不一样的创建时间和更新时间，附上默认值
        infoEntity.setCreateTime(new Date());
        infoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(infoEntity);

        //2、保存Spu的描述图片 pms_spu_info_desc
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        //id
        descEntity.setSpuId(infoEntity.getId());
        //描述，String.join 的方式将它们用逗号分隔
        descEntity.setDecript(String.join(",",decript));
        spuInfoDescService.saveSpuInfoDesc(descEntity);

        //3、保存spu的图片集 pms_spu_images
        //获取所有图片，并对应id保存
        List<String> images = vo.getImages();
        imagesService.saveImages(infoEntity.getId(),images);

        //4、保存spu的规格参数 pms_product_attr_value
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setAttrId(attr.getAttrId());
            // 可能页面没用传入属性名字 根据属性id查到所有属性 给名字赋值
            AttrEntity id = attrService.getById(attr.getAttrId());
            valueEntity.setAttrName(id.getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(infoEntity.getId());

            return valueEntity;
        }).collect(Collectors.toList());
        attrValueService.saveProductAttr(collect);

        //5、保存spu的积分信息；jxmall_sms->sms_spu_bounds
        //积分信息+id才是完整的信息
        Bounds bounds = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds,spuBoundTo);
        spuBoundTo.setSpuId(infoEntity.getId());
        R r = couponFeignService.saveSpuBounds(spuBoundTo);
        if(r.getCode() != 0){
            log.error("远程保存spu积分信息失败");
        }

        //6、保存当前spu对应的所有sku信息；
        List<Skus> skus = vo.getSkus();
        //信息较多，需要先进行遍历
        if(skus!=null && skus.size()>0){
            // 提前查找默认图片
            skus.forEach(item->{
                String defaultImg = "";
                for (Images image : item.getImages()) {
                    if(image.getDefaultImg() == 1){
                        defaultImg = image.getImgUrl();
                    }
                }
                //6.1）、sku的基本信息；pms_sku_info
                //skuName 、price、skuTitle、skuSubtitle 这些属性需要手动保存
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item,skuInfoEntity);
                skuInfoEntity.setBrandId(infoEntity.getBrandId());
                skuInfoEntity.setCatalogId(infoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(infoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                //保存
                skuInfoService.saveSkuInfo(skuInfoEntity);

                //自增主键
                Long skuId = skuInfoEntity.getSkuId();
                //6.2）、sku的图片信息；pms_sku_image
                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                    //进行过滤
                }).filter(entity->{
                    //返回true就是需要，false就是剔除
                    return !ObjectUtils.isEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());
                //保存
                skuImagesService.saveBatch(imagesEntities);
                //TODO 没有图片路径的无需保存

                //6.3）、sku的销售属性信息：pms_sku_sale_attr_value
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(a -> {
                    SkuSaleAttrValueEntity attrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, attrValueEntity);
                    attrValueEntity.setSkuId(skuId);

                    return attrValueEntity;
                }).collect(Collectors.toList());
                //保存
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                // //6.4）、sku的优惠、满减等信息；jxmall_sms->sms_sku_ladder\sms_sku_full_reduction\sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item,skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if(skuReductionTo.getFullCount() >0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1){
                    R r1 = couponFeignService.saveSkuReduction(skuReductionTo);
                    if(r1.getCode() != 0){
                        log.error("远程保存sku优惠信息失败");
                    }
                }
            });
        }

    }

    /**
     * 保存spu基本信息
     * @param infoEntity
     */
    @Override
    public void saveBaseSpuInfo(SpuInfoEntity infoEntity) {
        this.baseMapper.insert(infoEntity);
    }

    /**
     * spu管理模糊查询
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(!ObjectUtils.isEmpty(key)){
            //拼装模糊检索条件
            wrapper.and((w)->{
                w.eq("id",key).or().like("spu_name",key);
            });
        }
        // status=1 and (id=1 or spu_name like xxx)
        //直接进行eq即可
        String status = (String) params.get("status");
        if(!ObjectUtils.isEmpty(status)){
            wrapper.eq("publish_status",status);
        }

        String brandId = (String) params.get("brandId");
        if(!ObjectUtils.isEmpty(brandId)&&!"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }

        String catelogId = (String) params.get("catelogId");
        if(!ObjectUtils.isEmpty(catelogId)&&!"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }

        /**
         * 简单查询
         */
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);

    }

    /**
     * 商品上架
     * @param spuId
     */
    @Override
    public void up(Long spuId) {

        //1、查出当前spuId对应的所有sku信息,品牌的名字
        List<SkuInfoEntity> skuInfoEntities=skuInfoService.getSkusBySpuId(spuId);
        //TODO 4、根据spu查出当前sku的所有可以被用来检索的规格属性
        //提出来按照id查一遍规格属性即可
        List<ProductAttrValueEntity> productAttrValueEntities = attrValueService.list(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
        List<Long> attrIds = productAttrValueEntities.stream().map(attr -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());
        //过滤出检索属性的attr
        List<Long> searchIds=attrService.selectSearchAttrIds(attrIds);
        Set<Long> ids = new HashSet<>(searchIds);
        //返回true就是要用的
        List<SkuEsModel.Attr> searchAttrs = productAttrValueEntities.stream().filter(entity -> {
            return ids.contains(entity.getAttrId());
        }).map(entity -> {
            SkuEsModel.Attr attr = new SkuEsModel.Attr();
            BeanUtils.copyProperties(entity, attr);
            return attr;
            //收集到的集合就是要用的
        }).collect(Collectors.toList());

        //TODO 1、发送远程调用，库存系统查询是否有库存
        Map<Long, Boolean> stockMap = null;
        try {
            List<Long> longList = skuInfoEntities.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
            R skuHasStocks = wareFeignService.getSkuHasStock(longList);
            TypeReference<List<SkuHasStockVo>> typeReference = new TypeReference<List<SkuHasStockVo>>() {
            };
            stockMap = skuHasStocks.getData(typeReference).stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, SkuHasStockVo::getHasStock));
            log.warn("服务调用成功" + skuHasStocks);
        }catch (Exception e){
            log.error("远程调用库存服务失败,原因{}",e);
        }

        //2、封装每个sku的信息
        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuEsModel> skuEsModels = skuInfoEntities.stream().map(sku -> {
            //组装需要的数据
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, skuEsModel);
            skuEsModel.setSkuPrice(sku.getPrice());
            skuEsModel.setSkuImg(sku.getSkuDefaultImg());
            //TODO 2、热度评分。0
            skuEsModel.setHotScore(0L);
            //TODO 3、查询品牌和分类的名字信息
            //根据id查询品牌信息
            BrandEntity brandEntity = brandService.getById(sku.getBrandId());
            skuEsModel.setBrandName(brandEntity.getName());
            skuEsModel.setBrandImg(brandEntity.getLogo());
            //根据id查询分类的名字
            CategoryEntity categoryEntity = categoryService.getById(sku.getCatalogId());
            skuEsModel.setCatalogName(categoryEntity.getName());
            //设置可搜索属性
            skuEsModel.setAttrs(searchAttrs);
            //设置是否有库存
            skuEsModel.setHasStock(finalStockMap==null?false:finalStockMap.get(sku.getSkuId()));
            return skuEsModel;
        }).collect(Collectors.toList());


        //TODO 5、将数据发给es进行保存：jxmall-search
        R r = searchFeignService.productStatusUp(skuEsModels);
        if (r.getCode()==0){
            //修改当前spu状态
            this.baseMapper.updateSpuStatus(spuId, ProductConstant.StatusEnum.SPU_UP.getCode());
        }else {
            //TODO 6、重复调用，接口幂等性问题，重试机制
            /**
             * feign 调用流程
             * 1、构造请求数据，将对象转为json
             * 2、发送请求进行执行（执行成功会解码响应数据）
             * 3、执行请求会有重试机制
             *      while(true){
             *          try{
             *              executeAndDecode(template);
             *          }catch(){
             *              try{
             *                  retryer.continueOrPropagate(e);
             *              }catch(){
             *              thow ex;
             *              }
             *            continue;
             *         }
             *     }
             */
            log.error("商品远程es保存失败");
        }
    }

    /**
     * 根据skuId查出spu信息
     * @param skuId
     * @return
     */
    @Override
    public SpuInfoEntity getSpuInfoBySkuId(Long skuId) {

        SkuInfoEntity byId = skuInfoService.getById(skuId);
        Long spuId = byId.getSpuId();
        SpuInfoEntity spuInfoEntity = getById(spuId);

        return spuInfoEntity;

    }

}
