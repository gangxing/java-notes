package com.learn.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName Main
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 21/10/2018 20:47
 */
public class Main {
    private static ExecutorService pool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        testFirstTask();
    }


    /*
     * 测试当线程小于corePoolSize时 新提交的任务 是否还会加入queue中
     */
    private static void testFirstTask() {
        pool.execute(new Printer());
    }

    private static class Printer implements Runnable {

        @Override
        public void run() {
            System.err.println(Thread.currentThread().getName() + "开始执行" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println(Thread.currentThread().getName() + "执行完毕" + System.currentTimeMillis());


        }
    }


}
