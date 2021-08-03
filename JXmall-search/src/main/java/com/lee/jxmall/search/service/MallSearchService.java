package com.lee.jxmall.search.service;

import com.lee.jxmall.search.vo.SearchParamVo;
import com.lee.jxmall.search.vo.SearchResultVo;

public interface MallSearchService {

    /**
     *
     * @param param 检索的所有参数
     * @return  检索的结果，包含页面需要的所有信息
     */
    SearchResultVo search(SearchParamVo param);
}
