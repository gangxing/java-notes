package com.learn.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ThreadPoolExecutorTest
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 05/11/2018 09:57
 */
@Slf4j
public class ThreadPoolExecutorLearn {

    public static void main(String[] args) {
       launch();

    }

    public static void launch() {
        ExecutorService pool = initPool();

        for (int i = 0; i < 1000; i++) {
            MyTask task = new MyTask(String.valueOf(i));
            //mock long time
            Random random = new Random();
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                log.error("I'm interrupted", e);
            }

            log.info(task + " has been created");
            pool.execute(task);
        }

    }


    private static ExecutorService initPool() {
        int corePoolSize = 3;
        int maximumPoolSize = 6;
        long keepAliveTime = 100;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(3);
        ThreadFactory threadFactory = new ThreadFactory() {
            AtomicInteger num = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                //这个r并不是用户提交的r,而是Worker(它的任务就是不断地从队列中取任务，然后执行任务的run方法)
                //如果Worker直接用用户提交的任务，那执行完，这个线程就得死掉，没办法再次取得任务了
                return new Thread(r, "Work-Thread-" + num.getAndIncrement());
            }
        };
        RejectedExecutionHandler handler = (r, executor) -> {
            log.error("Task " + r + " has been rejected by pool " + executor);
            //do nothing
        };
        //最原始的构造器
        ThreadPoolExecutor executor= new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        executor.allowCoreThreadTimeOut(false);//default
        return executor;
    }

    private static ExecutorService initPool1() {
        return Executors.newFixedThreadPool(3);
    }


    private static class MyTask implements Runnable {

        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            log.info(this + " is running");
//            doBiz();
            log.info(this + " done");
        }

        private void doBiz() {
            Random random = new Random();
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                log.error("I'm interrupted", e);
            }
        }

        @Override
        public String toString() {
            return "MyTask-" + name;
        }
    }
}
