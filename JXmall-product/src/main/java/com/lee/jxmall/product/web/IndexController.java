package com.lee.jxmall.product.web;

import com.lee.jxmall.product.entity.CategoryEntity;
import com.lee.jxmall.product.service.CategoryService;
import com.lee.jxmall.product.vo.Catalog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

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
    @RequestMapping("/index/json/catalog.json")
    public Map<String, List<Catalog2Vo>> getCatalogJson() {

        Map<String, List<Catalog2Vo>> map = categoryService.getCatalogJson();
        return map;
    }

    /**
     * 简单测试
     * @return
     */
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        //1、获取一把锁，只要锁的名字一样就是一把锁
        RLock lock = redissonClient.getLock("mylock");

        //2、加锁
        lock.lock();//阻塞等待
        //  1）、锁的自动续期，如果业务超长，运行期间自动给锁续上新的30s
        //      不用担心业务时间长，锁自动过期删掉
        //  2）、加锁的业务只要运行完成，就不会给当前锁续期，即使不手动解锁，默认30s就会自动删除
        try {
            System.out.println("加锁成功，执行业务"+Thread.currentThread().getId());
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //3、解锁 放到finally
            System.out.println("释放锁"+Thread.currentThread().getId());
            lock.unlock();
        }

        return "hello";
    }

    /**
     * 写锁
     * 保证一定能读到最新数据，修改期间写锁是一个排他锁（互斥锁），读锁是一个共享锁
     * 写锁没释放读就必须等待
     *  写 + 读 ：等待写锁释放
     *  写 + 写 ：阻塞方式
     *  读 + 写 ：有读锁，写也需要等待
     *  读 + 读 ：相当于无锁，并发读，只会在redis中记录好，所有当前的读锁。他们都会同时加锁成功
     *  只要有写的存在，都必须等待
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/write")
    public String writeLock() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        String s = "";
        //1、改数据加写锁，读数据加读锁
        RLock rLock = lock.writeLock();
        rLock.lock();
        try {
            System.out.println("写锁加锁成功。。。" + Thread.currentThread().getId());
            s = UUID.randomUUID().toString();
            stringRedisTemplate.opsForValue().set("writeValue", s);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            System.out.println("写锁释放。。。" + Thread.currentThread().getId());
        }
        return s;
    }

    /**
     * 读数据
     * @return
     */
    @ResponseBody
    @GetMapping("/read")
    public String readLock() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        String s = "";
        //加读锁
        RLock rLock = lock.readLock();
        rLock.lock();
        try {
            System.out.println("读锁加锁成功。。。" + Thread.currentThread().getId());
            s = stringRedisTemplate.opsForValue().get("writeValue");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            System.out.println("读锁释放。。。" + Thread.currentThread().getId());
        }
        return s;
    }


    /**
     * 放假 锁门
     *
     * 5个班全部走完，就可以锁大门了
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @GetMapping("/lockDoor")
    public String lockDoor() throws InterruptedException {
        //闭锁
        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        door.trySetCount(5);
        //等待闭锁都完成
        door.await();
        return "放假。。。";
    }

    /**
     *
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @GetMapping("/gogo")
    public String gogo() throws InterruptedException {
        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        //计数-1，锁的数-1 (i--)
        door.countDown();
        long count = door.getCount();
        return "走了....剩余"+count;
    }


    /**
     * 信号量 模拟车库停车
     *  可以用作分布式限流
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @GetMapping("/park")
    public String park() throws InterruptedException {
        RSemaphore park = redissonClient.getSemaphore("park");
        //是阻塞式获取无返回值、一定要获取一个占位才能继续执行
        //park.acquire();
        //非阻塞、有量就返回true，无量返回false，继续向下执行
        boolean b = park.tryAcquire();
        if (b){
            return "ok";
        }else {
            return "没位置";
        }
    }

    @ResponseBody
    @GetMapping("/gocar")
    public String goCar(){
        RSemaphore park = redissonClient.getSemaphore("park");
        //释放一个信号 释放一个值 释放一个车位
        park.release();
        return "go";
    }

}
