package com.lee.jxmall.seckill.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *      @EnableScheduling 开启定时任务
 *      @Scheduled 开启一个定时任务
 *      自动配置类：TaskSchedulingAutoConfiguration
 * 异步任务
 *      @EnableAsync 开启异步任务
 *      @Async 给希望异步执行的方法标注
 *      自动配置类：TaskExecutionAutoConfiguration
 */
@Slf4j
//@Component
//@EnableAsync
//@EnableScheduling
public class HelloScheduled {

    /**
     * 1、在Spring中 只允许6位
     * 2、在周几的位置，1-7代表周一到周日，也可以用MON-SUN
     *      [* * * ? * 3] : 每周三每秒执行一次
     *      [* /5 * * ? * 3] : 每周三 每5秒执行一次
     * 3、定时任务不应阻塞 [默认是阻塞的]
     *      1）、让让业务以异步的方式，自己提交到线程池
     *          CompletableFuture.runAsync(() -> {
     *          },execute);
     *      2）、支持定时任务线程池；设置 TaskSchedulingProperties
     *          spring.task.scheduling.pool.size: 5
     *              不好用
     *      3）、让定时任务异步执行
     *
     *     解决：使用 “异步任务 + 定时任务“ 来完成定时任务不阻塞的功能
     */
    @Async
    @Scheduled(cron = "*/5 * * ? * 3")
    public void hello() {
        log.info("hello...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
    }
}
