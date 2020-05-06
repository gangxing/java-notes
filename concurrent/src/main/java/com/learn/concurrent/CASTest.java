package com.learn.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName CASTest
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 05/11/2018 23:28
 */
public class CASTest {

  public static void main(String[] args) throws Exception {
    MyAtomicInteger integer = new MyAtomicInteger(0);
    ExecutorService pool = Executors.newFixedThreadPool(100);
    int times = 10000000;
    CountDownLatch latch = new CountDownLatch(times);
//        CyclicBarrier barrier = new CyclicBarrier(times);
    for (int i = 0; i < times; i++) {
      pool.execute(new Adder(integer, latch));
    }
    latch.await();
    pool.shutdown();
    System.err.println(integer.getValue());
//        Assert.that(integer.getValue() == times, "不相等");
  }


  private static class Adder implements Runnable {

    private MyAtomicInteger integer;

    private CountDownLatch latch;

    private CyclicBarrier barrier;

    public Adder(MyAtomicInteger integer, CountDownLatch latch) {
      this.integer = integer;
      this.latch = latch;
    }

    @Override
    public void run() {
//            try {
//                barrier.await();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

      System.err.println(Thread.currentThread().getName() + "--->" + integer.getAndIncrement());
      latch.countDown();
    }
  }
}
