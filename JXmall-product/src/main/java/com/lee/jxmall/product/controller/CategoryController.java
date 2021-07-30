package com.lee.jxmall.product.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.jxmall.product.entity.CategoryEntity;
import com.lee.jxmall.product.service.CategoryService;
import com.lee.common.utils.R;



/**
 * 商品三级分类
 *
 * @author lee
 * @email 1114862851@qq.com
 * @date 2021-07-21 13:31:06
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 三级分类
     * 查出所有分类以及子分类，以树形结构组装
     */
    @RequestMapping("/list/tree")
    //@RequiresPermissions("product:category:list")
    public R list(){

        List<CategoryEntity> entityList = categoryService.listWithTree();

        return R.ok().put("data", entityList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 批量修改
     */
    @RequestMapping("/update/sort")
    //@RequiresPermissions("product:category:update")
    public R updateSort(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category){
        //级联更新
		categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * 删除
     * @RequestBody 获取请求体，必须发送post请求
     * springMVC 自动将请求体数据（json）转为对应的对象
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:category:delete")
    public R delete(@RequestBody Long[] catIds){
		//categoryService.removeByIds(Arrays.asList(catIds));

        //1、检查当前删除的菜单是否被别的地方引用
        categoryService.removeMenuByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
