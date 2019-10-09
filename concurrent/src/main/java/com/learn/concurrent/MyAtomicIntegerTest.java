package com.learn.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/9 16:02
 */
public class MyAtomicIntegerTest {

    public static void main(String[] args) {
        MyAtomicInteger count = new MyAtomicInteger(0);
        int times = 2000000;
        CountDownLatch latch = new CountDownLatch(times);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < times; i++) {
            pool.execute(() -> {
                count.incrementAndGet();
                latch.countDown();
            });
        }
        try {

            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.err.println(count.getValue()==times);
        pool.shutdown();
    }


}
