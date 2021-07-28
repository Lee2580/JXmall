package com.lee.jxmall.ware.service.impl;

import com.lee.common.constant.WareConstant;
import com.lee.jxmall.ware.entity.PurchaseDetailEntity;
import com.lee.jxmall.ware.service.PurchaseDetailService;
import com.lee.jxmall.ware.service.WareSkuService;
import com.lee.jxmall.ware.vo.MergeVo;
import com.lee.jxmall.ware.vo.PurchaseDoneVo;
import com.lee.jxmall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.ware.dao.PurchaseDao;
import com.lee.jxmall.ware.entity.PurchaseEntity;
import com.lee.jxmall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseDetailService purchaseDetailService;

    @Autowired
    WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询新建与未领取的采购单
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageUnreceivedPurchase(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
                        //新建和已分配状态的采购需求
                        .eq("status",0).or().eq("status",1)
        );
        return new PageUtils(page);
    }

    /**
     * 合并采购需求到采购单
     * TODO 确认采购单状态是0，1才可以合并
     * 已更新
     * @param mergeVo
     */
    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {

        Long purchaseId = mergeVo.getPurchaseId();
        //如果没有默认的采购单（没有选择采购单），需要新建采购单
        if(purchaseId==null){
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            //采购单的状态为新建状态
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);
            //得到采购单id
            purchaseId = purchaseEntity.getId();
        }

        //合并采购单 [其实就是修改上面创建的采购单]
        //获取带过来的采购需求
        List<Long> items = mergeVo.getItems();
        //TODO已更新 确认采购单状态是0，1才可以合并
        // 从数据库查询所有要合并的采购单并过滤所有大于 [已分配] 状态的订单
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        List<PurchaseDetailEntity> detailEntities = purchaseDetailService.getBaseMapper().selectBatchIds(items).stream().filter(entity -> {
            // 如果正在合并采购异常的项就把这个采购项之前所在的采购单的状态 wms_purchase 表的状态修改为 已分配
            if(entity.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()){
                purchaseEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
                purchaseEntity.setId(entity.getPurchaseId());
                this.updateById(purchaseEntity);
            }
            // 如果没还去采购，就可以更改
            // 采购需求有问题可以再去重新采购
            return entity.getStatus() < WareConstant.PurchaseDetailStatusEnum.BUYING.getCode() || entity.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode();
        }).collect(Collectors.toList());
        // 将符合条件的id集合重新赋值给 items
        items = detailEntities.stream().map(entity -> entity.getId()).collect(Collectors.toList());
        if(items == null || items.size() == 0){
            return;
        }

        // 设置仓库id   采购单得是同个仓库的
        purchaseEntity.setWareId(detailEntities.get(0).getWareId());
        Long finalPurchaseId = purchaseId;
        // 给采购单设置各种属性
        List<PurchaseDetailEntity> collect = items.stream().map((item) -> {
            //新建采购需求实体类
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(item);
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        //批量修改
        purchaseDetailService.updateBatchById(collect);

        //在合并完采购单后，更新采购单时间
        purchaseEntity.setUpdateTime(new Date());
        purchaseEntity.setId(purchaseId);
        this.updateById(purchaseEntity);

    }

    /**
     * 领取采购单
     * 过滤采购需求，并同步采购需求的状态
     * @param ids
     */
    @Override
    public void received(List<Long> ids) {

        // 没有采购需求直接返回，否则会破坏采购单
        if (ids == null || ids.size() == 0) {
            return;
        }

        //1、确认当前采购单是新建或者已分配状态
        //详细信息
        List<PurchaseEntity> collect = ids.stream().map(id -> {
            PurchaseEntity byId = this.getById(id);
            return byId;
            //过滤：当采购单的状态是新建和已分配时，就返回true
        }).filter(item -> {
            if (item.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() ||
                    item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode()) {
                return true;
            }
            return false;
            //改变采购单的状态为 已领取
        }).map(item->{
            item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());

        //2、更新采购单
        this.updateBatchById(collect);

        //3、改变采购项（采购需求）的状态
        collect.forEach((item)->{
            //entities：采购需求的集合
            List<PurchaseDetailEntity> entities = purchaseDetailService.listDetailByPurchaseId(item.getId());
            List<PurchaseDetailEntity> detailEntities = entities.stream().map(entity -> {
                PurchaseDetailEntity entity1 = new PurchaseDetailEntity();
                entity1.setId(entity.getId());
                //将采购状态改为正在采购
                entity1.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
                return entity1;
            }).collect(Collectors.toList());
            //更新采购需求
            purchaseDetailService.updateBatchById(detailEntities);
        });

    }

    /**
     * 完成采购
     *  1、改变采购单的状态
     *  2、改变采购项状态
     *  3、将成功采购的进行入库
     *
     * id：		采购单id
     * items：	采购项
     * itemId：	采购需求id
     * status：	采购状态
     *
     * @param purchaseDoneVo
     */
    @Override
    public void done(PurchaseDoneVo purchaseDoneVo) {

        Long id = purchaseDoneVo.getId();
        Boolean flag = true;
        List<PurchaseItemDoneVo> items = purchaseDoneVo.getItems();
        ArrayList<PurchaseDetailEntity> updates = new ArrayList<>();

        // 2.改变采购项状态
        for (PurchaseItemDoneVo item : items) {
            // 采购失败的情况
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            if (item.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()) {
                flag = false;
                detailEntity.setStatus(item.getStatus());
            } else {
                detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());
                // 3.将成功采购的进行入库
                // 查出当前采购项的详细信息
                PurchaseDetailEntity entity = purchaseDetailService.getById(item.getItemId());
                // skuId、到那个仓库、sku名字
                wareSkuService.addStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());
            }
            // 设置采购成功的id
            detailEntity.setId(item.getItemId());
            updates.add(detailEntity);
        }
        // 批量更新采购单
        purchaseDetailService.updateBatchById(updates);

        // 1.改变采购单状态
        // 对采购单的状态进行更新
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(id);
        purchaseEntity.setStatus(flag ? WareConstant.PurchaseStatusEnum.FINISH.getCode() : WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);

    }
}
