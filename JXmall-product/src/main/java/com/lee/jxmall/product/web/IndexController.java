package com.lee.jxmall.product.web;

import com.lee.jxmall.product.entity.CategoryEntity;
import com.lee.jxmall.product.service.CategoryService;
import com.lee.jxmall.product.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    /**
     * 首页一级分类
     * @param model
     * @return
     */
    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){

        //TODO 查出所有的一级分类
        List<CategoryEntity> categoryEntities= categoryService.getLevel1Categorys();

        model.addAttribute("categorys",categoryEntities);
        return "index";
    }

    /**
     * 首页二，三级分类
     * 返回json，而非跳转，加@ResponseBody
     * @return
     */
    @ResponseBody
    @RequestMapping("index/json/catalog.json")
    public Map<String, List<Catalog2Vo>> getCatalogJson() {

        Map<String, List<Catalog2Vo>> map = categoryService.getCatalogJson();
        return map;
    }
}
