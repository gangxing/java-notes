package com.learn.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/12 18:30
 */
@Slf4j
public class FutureLearn {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        //内部完全调用execute方法的 在哪里将结果放到FutureTask中的呢？？？
        //FutureTask
        /**
         * 大概看了下源码，关键在于提交给线程池的task的run
         * 对于普通task（没有返回结果），run主要是循环从队列中取出任务，并执行这里取出的任务就是业务上的
         * 对于FutureTask，前面也是一样，work的主要逻辑是从队列中取出任务，但这时取出的任务是FutureTask,其run逻辑是
         * 调用传进来的业务Callable.等待call方法结束，拿到结果后再设置到result中。
         *
         * 所以FutureTask和普通的Task，相当于多了一层。
         * 实现方案：
         * 在1.8之前，采用的AQS进行的同步，好像是因为什么中断，所以在1.8中改用一个int变量来控制任务的状态
         *
         * Future是可以cancel的。实现机制是通过给工作线程发送一个中断请求。那工作线程怎么响应的呢？？？？
         * 先使用一把
         * cancel，可以取消还未开始的任务，如果任务已经开始了，并且正在执行中，就通过mayInterruptIfRunning来判断
         * 是否尝试中断工作线程。
         * 问题：如果不尝试中断工作线程，cancel的意义是什么？对后续的get有什么影响？
         * 一旦cancel之后，get会直接抛出CancellationException,至于中断工作线程，只是一个尝试操作
         * 如果工作线程响应了（在业务run中），则任务就可以提前被取消掉，否则这个中断对业务run是没什么实质影响的
         * 一般cancel是当业务run执行时间过长，获取结果的线程不想等待了。所以cancel，但业务run如果没有响应中断请求(实际上
         * 大部分逻辑都不是等待的，所以基本没有响应中断请求)。业务run还是会继续执行下去。所以这个cancel。嗯，除了让所有等待结果的线程
         * 早点结束，并没有其他意义
         *
         */
        Future<Integer> future = pool.submit(new Task(1, 2));

        //后面的get会抛出CancellationException
//        future.cancel(true);
        future.isDone()


        Thread getResultThread = new Thread(new GetResult(future), "Get Result Thread");
        getResultThread.start();
        try {
            TimeUnit.MILLISECONDS.sleep(1000);

            future.cancel(true);

//            Integer result=future.get();
//            log.info("result is "+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
//        }catch (ExecutionException ee){
//            ee.printStackTrace();
        }

    }


    private static class GetResult implements Runnable {

        private Future<Integer> future;

        public GetResult(Future<Integer> future) {
            this.future = future;
        }

        @Override
        public void run() {
            try {
                Integer result = future.get();
                log.info("result is " + result);
            } catch (InterruptedException e) {
                log.warn("task has been cancelled");
            } catch (ExecutionException ee) {
                log.warn("task occurs exception");
            }
        }
    }

    private static class Task implements Callable<Integer> {

        private Integer a;

        private Integer b;

        public Task(Integer a, Integer b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Integer call() throws Exception {
            log.info("task is running");
            //只是模拟耗时长，实际业务中很少有这种场景。
            //这样是可以相应cancel的中断的
            Random random = new Random();
            TimeUnit.MILLISECONDS.sleep(random.nextInt(3 * 1000));

            int result = a + b;
            log.info("task finished result is " + result);
            return result;
        }
    }
}
