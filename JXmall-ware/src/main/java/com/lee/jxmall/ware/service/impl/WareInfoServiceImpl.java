package com.lee.jxmall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.lee.common.utils.R;
import com.lee.jxmall.ware.entity.WareSkuEntity;
import com.lee.jxmall.ware.feign.MemberFeignService;
import com.lee.jxmall.ware.vo.FareVo;
import com.lee.jxmall.ware.vo.MemberAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.ware.dao.WareInfoDao;
import com.lee.jxmall.ware.entity.WareInfoEntity;
import com.lee.jxmall.ware.service.WareInfoService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Autowired
    MemberFeignService memberFeignService;

    /**
     *  分页模糊查询仓库
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<WareInfoEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!ObjectUtils.isEmpty(key)) {
            wrapper.eq("id", key).or().like("name", key).or().like("address", key).or().like("areacode", key);
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);

    }

    /**
     * 根据收货地址获取运费
     * @param addrId
     * @return
     */
    @Override
    public FareVo getFare(Long addrId) {

        FareVo fareVo = new FareVo();
        R info = memberFeignService.addrInfo(addrId);
        MemberAddressVo address = info.getData("memberReceiveAddress", new TypeReference<MemberAddressVo>() {
        });

        if (address != null) {
            String phone = address.getPhone();
            //截取用户手机号码最后一位作为我们的运费计算
            //专业运费调用快递的接口
            String fare = phone.substring(phone.length() - 1);
            BigDecimal bigDecimal = new BigDecimal(fare);

            fareVo.setFare(bigDecimal);
            fareVo.setMemberAddressVo(address);

            return fareVo;
        }
        return null;

    }

}
