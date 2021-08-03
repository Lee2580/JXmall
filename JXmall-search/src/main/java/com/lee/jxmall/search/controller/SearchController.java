package com.lee.jxmall.search.controller;

import com.lee.jxmall.search.service.MallSearchService;
import com.lee.jxmall.search.vo.SearchParamVo;
import com.lee.jxmall.search.vo.SearchResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SearchController {

    @Autowired
    MallSearchService mallSearchService;

    /**
     * 自动将页面提交过来的所有请求查询参数封装成指定的对象
     * @param param
     * @return
     */
    @GetMapping("/list.html")
    public String listPage(SearchParamVo param, Model model, HttpServletRequest request){

        param.set_queryString(request.getQueryString());
        //1、根据传递来的页面的查询参数，去es中检索商品
        SearchResultVo result = mallSearchService.search(param);
        model.addAttribute("result",result);
        return "list";
    }
}
