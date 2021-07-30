package com.lee.jxmall.product.service.impl;


import com.lee.jxmall.product.service.CategoryBrandRelationService;
import com.lee.jxmall.product.vo.Catalog3Vo;
import com.lee.jxmall.product.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.product.dao.CategoryDao;
import com.lee.jxmall.product.entity.CategoryEntity;
import com.lee.jxmall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    //泛型是这个，可以不用注入
    /*@Autowired
    CategoryDao categoryDao;*/

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 三级分类
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //查出所有分类
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        /*
            组装成父子的树状结构
                1、找到所有的一级分类
         */
        List<CategoryEntity> level1Menus=categoryEntities.stream().filter((categoryEntity)->{
            //父分类id=0，说明是一级分类
            return categoryEntity.getParentCid() == 0;
        }).map((menu)->{
            //将当前菜单的子分类保存
            menu.setChildren(getChildrens(menu,categoryEntities));
            return menu;
        }).sorted((menu1,menu2)->{
            //排序
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());

        return level1Menus;
    }

    /**
     * 递归查找 所有菜单子菜单
     */
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){
        //过滤方法
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            //相等说明当前菜单就是这个菜单的子菜单
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            //保存这个菜单的子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2)->{
            //排序
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }

    //删除
    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 检查当前删除的菜单是否被别的地方引用

        //逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelongPath(Long catelogId) {
        List<Long> paths=new ArrayList<>();

        List<Long> parentPath = findParentPath(catelogId, paths);
        // 收集的时候是顺序 前端是逆序显示的 所以用集合工具类给它逆序一下
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);

    }

    /**
     * 级联更新所有关联的数据
     * @param category
     * @Transactional 表示一个事务
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    /**
     * 查询所有的一级分类
     * @return
     */
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        //long l = System.currentTimeMillis();
        // parent_cid,0 ;  cat_level,1
        List<CategoryEntity> categoryEntities = baseMapper.selectList(
                new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));

        //System.out.println("消耗时间 = "+(System.currentTimeMillis()-l));
        return categoryEntities;
    }

    /**
     * 第一次查询的所有 CategoryEntity 然后根据 parent_cid去这里找
     */
    private List<CategoryEntity> getCategoryEntities(List<CategoryEntity> entityList, Long parentCid) {

        return entityList.stream().filter(item -> item.getParentCid().equals(parentCid)).collect(Collectors.toList());
    }

    /**
     * 查出所有的分类
     * @return
     */
    //@Cacheable(value = {"category"}, key = "#root.methodName")
    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {

        List<CategoryEntity> entityList = baseMapper.selectList(null);
        // 查询所有一级分类
        List<CategoryEntity> level1 = getCategoryEntities(entityList, 0L);
        //封装数据
        Map<String, List<Catalog2Vo>> parent_cid = level1.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 拿到每一个一级分类 然后查询他们的二级分类
            List<CategoryEntity> entities = getCategoryEntities(entityList, v.getCatId());
            //封装上面的结果
            List<Catalog2Vo> catalog2Vos = null;
            if (entities != null) {
                catalog2Vos = entities.stream().map(l2 -> {
                    Catalog2Vo catalog2Vo = new Catalog2Vo(v.getCatId().toString(), l2.getName(), l2.getCatId().toString(), null);
                    // 找当前二级分类的三级分类
                    List<CategoryEntity> level3 = getCategoryEntities(entityList, l2.getCatId());
                    // 三级分类有数据的情况下
                    if (level3 != null) {
                        List<Catalog3Vo> catalog3Vos = level3.stream().map(l3 -> new Catalog3Vo(l3.getCatId().toString(), l3.getName(), l2.getCatId().toString())).collect(Collectors.toList());
                        catalog2Vo.setCatalog3List(catalog3Vos);
                    }
                    return catalog2Vo;
                }).collect(Collectors.toList());
            }
            return catalog2Vos;
        }));
        return parent_cid;
    }

    /**
     * 递归收集所有父节点
     */
    private List<Long> findParentPath(Long catlogId, List<Long> paths) {
        // 1、收集当前节点id
        paths.add(catlogId);
        CategoryEntity byId = this.getById(catlogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

}
