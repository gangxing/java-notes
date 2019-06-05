package com.learn.concurrent.lock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName CountDownLatchTest
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 30/10/2018 21:19
 */
public class CountDownLatchTest {

    /*
     *
     * 当前线程等待其他线程执行完毕后countDown 直到计数降至0是  当前线程再执行await后面的逻辑
     * 当指定的countDown数量为1时 可作为开关使用 当新线程执行完某个逻辑后 当前线程在继续执行
     * await 实现原理
     * https://www.jianshu.com/p/fe027772e156
     *
     * AQS即是AbstractQueuedSynchronizer
     *
     * await 还可以被多个线程使用 当countDown计数变为0时 所有线程被唤醒
     */
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        for (int i=0;i<3;i++){
            new Thread(new Master(latch)).start();
            new Thread(new Worker(latch)).start();
        }
        String mainName = Thread.currentThread().getName();
        System.err.println(mainName + "开始执行");
//        new Thread(new Worker(latch)).start();
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.err.println(mainName + "执行完毕");
    }


    private static class Master implements Runnable {

        private CountDownLatch latch;

        public Master(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            String name = "Master" + Thread.currentThread().getName();
            System.err.println(name + "开始执行");
            try {

                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println(name + "执行完毕");

        }
    }

    private static class Worker implements Runnable {

        private CountDownLatch latch;

        Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            String name = "Worker" + Thread.currentThread().getName();
            System.err.println(name + "开始执行");
            try {
                Random random = new Random();
                Thread.sleep(random.nextInt(20000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.err.println(name + "执行完毕");
            latch.countDown();
        }
    }
}
