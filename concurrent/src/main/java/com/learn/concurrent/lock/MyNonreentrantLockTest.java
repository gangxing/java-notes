package com.learn.concurrent.lock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/9 14:20
 */
public class MyNonreentrantLockTest {

  //        private ReentrantLock lock = new ReentrantLock();
  private MyNonreentrantLock lock = new MyNonreentrantLock();

  private int count;

  private ExecutorService pool = Executors.newFixedThreadPool(10, new ThreadFactory() {
    AtomicInteger num = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
      Thread t = new Thread(r);
      t.setName("MyRun-" + num.getAndIncrement());
      return t;
    }
  });


  public static void main(String[] args) {
    /*
     * 两个层面的功能
     * 1.锁 功能已实现 主要是实现tryAcquire 和tryRelease两个方法
     * 2.非重入型
     */
    MyNonreentrantLockTest app = new MyNonreentrantLockTest();
    int times = 2;
    CountDownLatch latch = new CountDownLatch(times);
    for (int i = 0; i < times; i++) {
      app.pool.execute(app.new MyRun(latch));
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    app.pool.shutdown();
    app.print("执行完毕,结果 count=" + app.count + (app.count == times));

  }

  private void print(String s) {
    System.err.println(Thread.currentThread().getName() + ":" + s);
  }

  private void sleep() {
    try {
      Random random = new Random();
      Thread.sleep(random.nextInt(800));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  private class MyRun implements Runnable {

    private CountDownLatch latch;

    public MyRun(CountDownLatch latch) {
      this.latch = latch;
    }

    @Override
    public void run() {
//            print("尝试获取锁 count=" + count);
      if (lock.tryLock()) {
        print("第一次获取到锁");
        try {
//                print("获得锁，执行中...");
          if (lock.tryLock()) {
            print("第二次获取到锁");
            try {
              count++;
            } finally {
              lock.unlock();
            }
          } else {
            print("第二次未获取到锁");
          }
//                    sleep();

        } finally {
          latch.countDown();
          lock.unlock();
//                print("执行完毕，释放锁 count=" + count);
        }
      }
    }
  }
}
