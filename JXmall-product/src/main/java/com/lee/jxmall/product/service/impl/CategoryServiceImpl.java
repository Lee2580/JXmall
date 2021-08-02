package com.lee.jxmall.product.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lee.jxmall.product.service.CategoryBrandRelationService;
import com.lee.jxmall.product.vo.Catalog3Vo;
import com.lee.jxmall.product.vo.Catalog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
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
import org.springframework.util.ObjectUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    //泛型是这个，可以不用注入
    /*@Autowired
    CategoryDao categoryDao;*/

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

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
     * @CacheEvict 失效模式 删除 触发将数据从缓存删除的操作
     *      key 不加单引号会认为是动态取值
     * @CachePut 双写模式
     *
     * 1、同时进行多种缓存操作         @Caching
     * 2、指定删除某个分区下的所有数据   @CacheEvict(value = "category",allEntries = true)
     * 3、存储同一类型的数据，都可以指定成同一个分区。 分区名默认就是缓存的前缀
     *
     */
    //@CacheEvict(value = "category",key = "'getLevel1Categorys'")
    /*@Caching(evict = {
            @CacheEvict(value = "category",key = "'getLevel1Categorys'"),
            @CacheEvict(value = "category",key = "'getCatalogJson'")
    })*/
    @CacheEvict(value = "category",allEntries = true)
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    /**
     * 查询所有的一级分类
     * 1、@Cacheable 代表当前方法的结果需要缓存
     *      如果缓存中有，方法不用调用
     *      如果缓存中没有，会调用方法，最后将方法的结果放入缓存
     *  2、每个需要缓存的数据我们都来指定要放到那个名字的缓存【缓存的分区，按照业务类型分】
     *  3、默认行为
     *      1）、如果缓存中有，方法不用调用
     *      2）、key默认自动生成缓存的名字：SimpleKey []（自主生成的key值） category::SimpleKey []
     *      3）、缓存的value的值，默认使用jdk序列化机制，将序列化后的数据存到redis
     *      4）、默认时间 ttl：-1
     *     自定义
     *      1）、指定生成的缓存使用的key        key属性指定，接收一个SpEL
     *      2）、指定缓存的数据的存活时间        配置文件中设置ttl
     *      3）、将数据保存为json格式
     *  4、springCache的不足
     *      1）、读模式
     *          缓存穿透    解决：缓存空数据 cache-null-values: true
     *          缓存击穿    解决：加锁？
     *          缓存雪崩    解决：加随机时间 time-to-live: 3600000
     *     2）、写模式（缓存与数据库一致）
     *          读写加锁
     *          引入Canal
     *          读多写多，直接去数据库查询
     *     总结：
     *          常规数据（读多写少，即时性，一致性要求不高的数据）：完全可以使用SpringCache，写模式只要有缓存数据的过期时间就足够了
     *          特殊数据：特殊设计
     *     原理：
     *          CacheManager(RedisCacheManager) -> Cache(RedisCache) -> cache负责缓存的读写
     *          源码中，只有get方法有锁，其他都没有锁
     *
     * @return
     */
    @Cacheable(value = {"category"},key = "#root.method.name",sync = true)
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
     * 查出所有的分类 2.0
     *      利用SpringCache实现缓存
     * @return
     */
    @Cacheable(value = {"category"},key = "#root.methodName")
    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {

        System.out.println("查询了数据库==================");
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
     * 查出所有的分类 1.0
     *      已淘汰
     * 利用redis缓存
     *
     * 给缓存中放json字符串，拿出的json字符串，还要逆转为能用的对象类型 【序列化与反序列化】
     * @return
     */
    //@Override
    public Map<String, List<Catalog2Vo>> getCatalogJson2() {

        /**
         * 1、设置空结果缓存，解决缓存穿透
         * 2、设置过期时间（加随机值），解决雪崩问题
         * 3、加锁，解决缓存击穿问题
         */

        //1、加入缓存逻辑，缓存中存的数据是json字符串
        //JSON 跨语言跨平台
        String catalogJson = stringRedisTemplate.opsForValue().get("catalogJson");
        if (ObjectUtils.isEmpty(catalogJson)) {
            //2、缓存中没查到，查询数据库
            System.out.println("缓存不命中==============查询数据库============");
            Map<String, List<Catalog2Vo>> catalogJsonFromDB = getCatalogJsonFromDBWithRedisLock();

            return catalogJsonFromDB;
        }
        //缓存中查到，将JSON转为指定的对象再返回
        System.out.println("缓存命中========================");
        Map<String, List<Catalog2Vo>> stringListMap =
                JSON.parseObject(catalogJson,
                        new TypeReference<Map<String, List<Catalog2Vo>>>() {});
        return stringListMap;
    }

    /**
     * 查出所有的分类  【本地锁】
     * 从数据库查询并封装分类数据
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDBWithLocalLock() {

        /**
         * 只要是同一把锁，就能锁住需要这个锁的所有线程
         * 1、 synchronized (this) ，因为springboot所有的组件在容器中是单例的
         * TODO 本地锁 synchronized，JUC(Lock) 在分布式情况下，想锁住所有，必须使用分布式锁
         */
        synchronized (this) {
            //得到锁之后再去缓存中确定一次，如果没有才需要继续查询
            return getDataFormDB();
        }
    }

    /**
     * 查出所有的分类  【分布式锁 Redis】
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDBWithRedisLock() {

        String uuid = UUID.randomUUID().toString();
        //1、占分布式锁，去redis占坑
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid,300,TimeUnit.SECONDS);
        if (lock){
            System.out.println("获取分布式锁成功=============");
            //加锁成功  执行业务
            //2、设置过期时间，必须和加锁同步，原子性的
            Map<String, List<Catalog2Vo>> dataFromDB;
            try{
                dataFromDB = getDataFormDB();
            }finally {
                // 删除也必须是原子操作 Lua脚本操作 删除成功返回1 否则返回0
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                // 原子删锁
                stringRedisTemplate.execute(
                        new DefaultRedisScript<Long>(script, Long.class), // 脚本和返回类型
                        Arrays.asList("lock"), // 参数
                        uuid); // 参数值，锁的值
            }
            return dataFromDB;
        }else {
            //加锁失败  等待重试（自旋方式）
            //等200毫秒
            try {
                System.out.println("获取分布式锁失败=============");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getCatalogJsonFromDBWithRedisLock();
        }
    }


    /**
     * 查出所有的分类  【分布式锁 Redisson】
     *  缓存里的数据如何和数据库保持一致
     *  缓存数据一致性
     *    1）、双写模式
     *    2）、失效模式
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDBWithRedissonLock() {

        //1、锁的名字    锁的粒度，越细越快
        //锁的粒度，具体缓存的是某个数据，10号商品：product-10-lock
        RLock lock = redissonClient.getLock("CatalogJson-lock");
        lock.lock();

        Map<String, List<Catalog2Vo>> dataFromDB;
        try {
            dataFromDB = getDataFormDB();
        } finally {
            lock.unlock();
        }
        return dataFromDB;
    }


    /**
     * 从数据库中查询出所有的分类
     * @return
     */
    private Map<String, List<Catalog2Vo>> getDataFormDB() {

        //得到锁之后再去缓存中确定一次，如果没有才需要继续查询
        String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");
        if (!ObjectUtils.isEmpty(catalogJSON)) {
            //缓存不为null，直接返回
            return JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
            });
        }
        System.out.println("查询了数据库==================");
        /**
         * 将数据库的多次查询变为一次
         */
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

        //3、查询到的数据先转为JSON再放入缓存
        String s = JSON.toJSONString(parent_cid);
        //放入缓存，设置1天过期时间
        stringRedisTemplate.opsForValue().set("catalogJson", s, 1, TimeUnit.DAYS);
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
