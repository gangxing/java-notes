package com.learn.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/11 11:50
 */

/**
 * https://blog.csdn.net/tyrroo/article/details/81390202
 * ForkJoinPool也是一个线程池，支持ThreadPoolExecutor同样的功能。
 * 但如果把ForkJoinPool当做ThreadPoolExecutor来用，那ForkJoinPool就没有存在的意义了不是？
 * ForkJoinPool的关键在于fork和join。什么意思呢，
 * 举个例子。假如有一个很耗时的任务，刚好这个任务是可以分解成多个小任务的。如果这时候用ThreadPoolExecutor
 * 那就发挥不了多线程（特别是多核条件下的并行计算优势）。这时候将一个大任务分解成足够小的小任务，每个线程执行一个
 * 最后将各个小任务的计算结果合并。
 * ForkJoinPool接收ForkJoinTask，它有fork和join两个操作，其中fork就是分解任务，join就是合并计算结果
 * 举个例子，给定一个起始数字和一个截止数字，计算从起始数字到截止数字的和，比如[1,3]=1+2+3=6。为了体现"加"操作很耗时
 * 随机睡一会儿，然后看看效果
 * 类似还有归并排序，天然就是先分解 再合并
 * 另外 java8新增的各种并行计算 都是这个套路
 * fork join 跟 map reduce 有点相似
 * 那么 在实际项目中，有没有适用场景呢？？？
 * 比如项目中每天有各种统计？分页查数据 ->遍历汇总 ->插入汇总表
 * 一般操作开启一个线程 逐页执行。这种场景下就可以用ForkJoinPool来实现。指定每个线程查询的范围(分页参数)
 *
 *
 * 了解了使用姿势后，大概看看ForkJoinPool的实现方案
 * 有个核心的思想 work steal
 *
 * TODO 归并排序 并行计算
 */
@Slf4j
public class ForkJoinPoolLearn {

    //每个任务最大的计算范围，如果超过了就继续拆解
    private static final int MAX_RANGE = 10000;

    public static void main(String[] args)throws Exception {
        ForkJoinPoolLearn app = new ForkJoinPoolLearn();
//        ForkJoinPool.commonPool()
        ForkJoinPool pool = new ForkJoinPool();

        int start = 1;
        int end = 999999999;
//        ForkJoinCountTask task = app.new ForkJoinCountTask(start, end);
        CountTask task=app.new CountTask(start,end);
        long startTime = System.currentTimeMillis();
//        Integer sum = pool.invoke(task);
        ForkJoinTask<Integer> result=pool.submit(task);

        Integer sum=result.get();
        long time = System.currentTimeMillis() - startTime;
        log.info("sum( {} , {} ) = {}", start, end, sum);
        log.info("duration {} ms", time);
    }

    private class ForkJoinCountTask extends RecursiveTask<Integer> {
        private Integer start;

        private Integer end;

        public ForkJoinCountTask(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int range = end - start;
            //分解任务
            if (range > MAX_RANGE) {
                int mid = start + range / 2;
                ForkJoinCountTask task1 = new ForkJoinCountTask(start, mid);
                task1.fork();
                ForkJoinCountTask task2 = new ForkJoinCountTask(mid + 1, end);
                task2.fork();
                return task1.join() + task2.join();
            }
            return doCount(start, end);

        }
    }

    private class CountTask implements Callable<Integer> {

        private Integer start;

        private Integer end;

        public CountTask(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            return doCount(start, end);
        }
    }

    private Integer doCount(Integer start, Integer end) {
//        log.info("开始计算sum({},{}) range={}", start, end, end - start + 1);
        //任务已经足够小了 直接执行
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }

}
