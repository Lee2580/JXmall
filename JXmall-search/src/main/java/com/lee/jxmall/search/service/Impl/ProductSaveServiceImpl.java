package com.lee.jxmall.search.service.Impl;

import com.alibaba.fastjson.JSON;
import com.lee.common.to.es.SkuEsModel;
import com.lee.jxmall.search.config.JXmallESConfig;
import com.lee.jxmall.search.constant.ESConstant;
import com.lee.jxmall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 上架保存sku数据
     * 保存到es
     * @param skuEsModels
     * @return
     */
    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {

        // 1.给ES建立一个索引 product
        BulkRequest bulkRequest = new BulkRequest();
        // 2.构造保存请求
        for (SkuEsModel esModel : skuEsModels) {
            // 设置索引
            IndexRequest indexRequest = new IndexRequest(ESConstant.PRODUCT_INDEX);
            // 设置索引id
            indexRequest.id(esModel.getSkuId().toString());
            // 数据内容
            String jsonString = JSON.toJSONString(esModel);
            indexRequest.source(jsonString, XContentType.JSON);
            // add
            bulkRequest.add(indexRequest);
        }
        // bulk批量保存
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, JXmallESConfig.COMMON_OPTIONS);
        // TODO 是否拥有错误 错误批量处理
        boolean hasFailures = bulk.hasFailures();
        if(hasFailures){
            List<String> collect = Arrays.stream(bulk.getItems()).map(item ->{
                return item.getId();
            }).collect(Collectors.toList());
            log.info("商品上架成功：{}，返回数据：{}",collect,bulk);
        }
        return hasFailures;
    }
}
