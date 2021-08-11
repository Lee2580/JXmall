package com.lee.jxmall.coupon.service.impl;

import com.lee.jxmall.coupon.entity.SkuFullReductionEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.coupon.dao.SeckillSkuRelationDao;
import com.lee.jxmall.coupon.entity.SeckillSkuRelationEntity;
import com.lee.jxmall.coupon.service.SeckillSkuRelationService;
import org.springframework.util.ObjectUtils;


@Service("seckillSkuRelationService")
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity> implements SeckillSkuRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<SeckillSkuRelationEntity> queryWrapper = new QueryWrapper<SeckillSkuRelationEntity>();

        String promotionSessionId = (String) params.get("promotionSessionId");

        // 封装活动场次id，关联
        if (!ObjectUtils.isEmpty(promotionSessionId)) {
            queryWrapper.eq("promotion_session_id", promotionSessionId);
        }
        IPage<SeckillSkuRelationEntity> page = this.page(
                new Query<SeckillSkuRelationEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}
