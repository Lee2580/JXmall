package com.lee.jxmall.coupon.service.impl;

import com.lee.jxmall.coupon.entity.SeckillSkuRelationEntity;
import com.lee.jxmall.coupon.service.SeckillSkuRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.coupon.dao.SeckillSessionDao;
import com.lee.jxmall.coupon.entity.SeckillSessionEntity;
import com.lee.jxmall.coupon.service.SeckillSessionService;


@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {

    @Autowired
    SeckillSkuRelationService seckillSkuRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
                new Query<SeckillSessionEntity>().getPage(params),
                new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询最近三天的秒杀活动及参与秒杀的商品
     * @return
     */
    @Override
    public List<SeckillSessionEntity> getLates3DaySession() {

        //计算最近三天
        //查出这三天参与秒杀活动的
        List<SeckillSessionEntity> list = this.list(new QueryWrapper<SeckillSessionEntity>()
                .between("start_time", startTime(), endTime()));
        //根据活动查出所有的商品
        if (list != null && list.size() > 0) {
            List<SeckillSessionEntity> collect = list.stream().map(session -> {
                //当前活动id
                Long id = session.getId();
                //查出sms_seckill_sku_relation表中关联的skuId
                List<SeckillSkuRelationEntity> relationSkus = seckillSkuRelationService.list(
                        new QueryWrapper<SeckillSkuRelationEntity>().eq("promotion_session_id", id));
                session.setRelationSkus(relationSkus);
                return session;
            }).collect(Collectors.toList());
            return collect;
        }
        return null;
    }

    /**
     * 当前时间
     * @return
     */
    private String startTime() {

        //当前日期  最小时间
        LocalDate now = LocalDate.now();
        LocalTime min = LocalTime.MIN;
        LocalDateTime start = LocalDateTime.of(now, min);

        //格式化时间
        String startFormat = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return startFormat;
    }

    /**
     * 结束时间
     * @return
     */
    private String endTime() {

        //加2天的日期    最大时间
        LocalDate now = LocalDate.now();
        LocalDate plus = now.plusDays(2);
        LocalTime max = LocalTime.MAX;
        LocalDateTime end = LocalDateTime.of(plus, max);

        //格式化时间
        String endFormat = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return endFormat;
    }

}
