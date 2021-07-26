package com.lee.jxmall.product.service.impl;

import com.lee.jxmall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.product.dao.BrandDao;
import com.lee.jxmall.product.entity.BrandEntity;
import com.lee.jxmall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import static org.springframework.util.StringUtils.hasLength;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        //获取key
        String key = (String) params.get("key");
        if((!ObjectUtils.isEmpty(key))){
            // 字段等于  or  模糊查询
            wrapper.eq("brand_id", key).or().like("name", key);
        }
        // 按照分页信息和查询条件  进行查询
        IPage<BrandEntity> page = this.page(
                // 传入一个IPage对象，他是接口，实现类是Page
                new Query<BrandEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);

    }

    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        //保证冗余字段的数据一致性
        this.updateById(brand);
        if (!ObjectUtils.isEmpty(brand.getName())){
            //同步更新其他关联表中的数据
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());

            //TODO 更新其他关联
        }

    }

}
