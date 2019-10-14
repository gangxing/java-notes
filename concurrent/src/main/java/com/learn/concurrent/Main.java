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
     * 非核心线程是对任务队列的一种支持，当核心线程还未满的时候，直接创建一个
     * 当核心线程满了，尝试加入到队列
     * 当队列也满了之后，如果还有非核心线程可创建的话，才会创建一个新线程。
     * 队列和非核心线程都满了，当然就拒绝接收任务了
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
