package com.learn.concurrent.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ThreadPoolExecutorTest
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 05/11/2018 09:57
 */
public class ThreadPoolExecutorLearn {
    static Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorLearn.class);

    public static void main(String[] args) {
        ExecutorService pool = initPool();

        for (int i = 0; i < 100; i++) {
            MyTask task=new MyTask(String.valueOf(i));
            //mock long time
            Random random = new Random();
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                logger.error("I'm interrupted", e);
            }

            logger.info(task+" has been created");
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
                return new Thread(r,"Work-Thread-" + num.getAndIncrement());
            }
        };
        RejectedExecutionHandler handler = (r, executor) -> {
            logger.error("Task " + r + " has been rejected by pool " + executor);
            //do nothing
        };
        //最原始的构造器
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
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
            logger.info(this + " is running");
            doBiz();
            logger.info(this + " done");
        }

        private void doBiz() {
            Random random = new Random();
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                logger.error("I'm interrupted", e);
            }
        }

        @Override
        public String toString() {
            return "MyTask-" + name;
        }
    }
}
