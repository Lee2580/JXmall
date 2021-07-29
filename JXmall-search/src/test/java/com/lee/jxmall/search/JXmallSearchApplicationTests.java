package com.lee.jxmall.search;

import com.alibaba.fastjson.JSON;
import com.lee.jxmall.search.bean.Account;
import com.lee.jxmall.search.config.JXmallESConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.InternalOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class JXmallSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    void contextLoads() {
        System.out.println(client);
    }

    @Data
    class User{
        private String userName;
        private String gender;
        private int age;
    }

    /**
     * 保存数据
     * @throws IOException
     */
    @Test
    void indexData() throws IOException {

        // 设置索引
        IndexRequest indexRequest = new IndexRequest ("users");
        indexRequest.id("1");

        User user = new User();
        user.setUserName("张三");
        user.setAge(20);
        user.setGender("男");
        String jsonString = JSON.toJSONString(user);

        //设置要保存的内容，指定数据和类型
        indexRequest.source(jsonString, XContentType.JSON);

        //执行创建索引和保存数据
        IndexResponse index = client.index(indexRequest, JXmallESConfig.COMMON_OPTIONS);

        System.out.println(index);

    }

    /**
     * 复杂检索
     * @throws IOException
     */
    @Test
    void find() throws IOException {

        // 1 创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("bank");
        // 检索条件，指定DSL
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //  构造检索条件
        /* 根操作都可以找到
        sourceBuilder.query();
        sourceBuilder.from();
        sourceBuilder.size();
        sourceBuilder.aggregation();*/
        //QueryBuilder的工具类
        sourceBuilder.query(QueryBuilders.matchQuery("address","mill"));

        //AggregationBuilders工具类构建AggregationBuilder
        // 构建第一个聚合条件:按照年龄的值分布
        // 聚合名称
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
        // 参数为AggregationBuilder
        sourceBuilder.aggregation(ageAgg);
        // 构建第二个聚合条件:平均薪资
        AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");
        sourceBuilder.aggregation(balanceAvg);

        System.out.println("检索条件"+sourceBuilder.toString());

        searchRequest.source(sourceBuilder);

        // 2 执行检索
        SearchResponse response = client.search(searchRequest, JXmallESConfig.COMMON_OPTIONS);
        // 3 分析响应结果
        System.out.println(response.toString());
        //  3.1）、获取所有查到的数据
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            Account account = JSON.parseObject(sourceAsString, Account.class);
            System.out.println(account);
        }
        //  3.2）、获得检索到的分析信息
        Aggregations aggregations = response.getAggregations();
        /*
        for (Aggregation aggregation : aggregations.asList()) {
            System.out.println("当前聚合"+aggregation.getName());
        }*/
        Terms ageAgg1 = aggregations.get("ageAgg");
        for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println("年龄 = " + keyAsString+"，有"+bucket.getDocCount()+"人");
        }

        Avg balanceAvg1 = aggregations.get("balanceAvg");
        System.out.println("平均薪资 = "+balanceAvg1.getValue());
    }

}
