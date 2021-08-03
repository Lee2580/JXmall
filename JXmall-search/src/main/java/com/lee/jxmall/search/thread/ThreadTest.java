package com.lee.jxmall.search.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {

    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main..........start.........");

        /*
        CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
            System.out.println("this is "+Thread.currentThread().getId());
            int i=10/2;
            System.out.println("i = "+i);
        },executorService);*/

/*        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {

            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 0;
            System.out.println("运行结果：" + i);
            return i;
        }, executorService).whenComplete((ret,exec)->{
            //能得到异常音系，但是无法修改返回数据
            System.out.println("异步任务完成....结果："+ret+"，异常："+exec);
        }).exceptionally(throwable -> {
            //可以感知异常，同时返回默认值
            return 10;
        });*/

        /**
         * 方法执行完成后的处理
         */
        /*CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {

            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, executorService).handle((ret,thr)->{
            if (ret!=null){
                return  ret*2;
            }
            if (thr!=null){
                return 0;
            }
            return 0;
        });*/

        /**
         * 线程串行化
         *  thenRunAsync：不能获取上一步的执行结果，无返回值
         *  thenAcceptAsync:能接收上一步结果，无返回值
         *  thenApplyAsync：能接收上一步结果，有返回值
         */
     /*   CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {

            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, executorService).thenApplyAsync(ret->{
            System.out.println("任务2.。。。。。。。。。"+ret);
            return "thenApply"+ret;
        }, executorService);*/

        /**
         * 两个都完成
         */
        CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(() -> {

            System.out.println("线程11111：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("1111运行结果：" + i);
            return i;
        }, executorService);

        CompletableFuture<Object> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("线程2222：" + Thread.currentThread().getId());

            try {
                Thread.sleep(2000);
                System.out.println("2222运行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        },executorService);
        /*
        //不能感知到结果
        future1.runAfterBothAsync(future2,()->{
            System.out.println("线程33333333");
        },executorService);
        //能感知到结果
        future1.thenAcceptBothAsync(future2,(f1,f2)->{
            System.out.println("线程thenAcceptBothAsync=="+f1+"=="+f2);
        },executorService);
        //能感知结果，有返回值
        CompletableFuture<String> future = future1.thenCombineAsync(future2, (f1, f2) -> {
            return f1 + "----" + f2 + "----线程thenCombineAsync";
        }, executorService);*/

        /**
         * 只要一个完成就执行任务
         */
        //不感知结果，也无返回值
        future1.runAfterEitherAsync(future2,()->{
            System.out.println("线程33333333");
        },executorService);
        //感知结果，无返回值
        future1.acceptEitherAsync(future2,(ret)->{
            System.out.println("线程acceptEitherAsync==="+ret);
        },executorService);
        //感知结果，有返回值
        CompletableFuture<String> future = future1.applyToEitherAsync(future2, (ret) -> {
            return "线程applyToEitherAsync----" + ret;
        }, executorService);

        System.out.println("main.....end.........."+future.get());


        CompletableFuture<String> futureImg = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询图片信息");
            return "hello.jpg";
        }, executorService);

        CompletableFuture<String> futureAttr = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品的属性");
            return "黑色+256G";
        }, executorService);

        CompletableFuture<String> futureDesc = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品介绍");
            return "华为";
        }, executorService);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futureImg, futureAttr, futureDesc);
        //等待所有结果完成
        allOf.get();
        System.out.println("main...end..."+futureImg.get()+"---"+futureAttr.get()+"---"+futureDesc.get());

        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(futureImg, futureAttr, futureDesc);
        //获得了最先完成的结果
        System.out.println("main...end..."+anyOf.get());
    }
}
