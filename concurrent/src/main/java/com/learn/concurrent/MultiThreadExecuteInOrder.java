package com.learn.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName MultiThreadExecuteInOrder
 * @Description 启用3个线程 依次输出ABC
 * @Author xianjun@ixiaopu.com
 * @Date 29/10/2018 21:52
 */
public class MultiThreadExecuteInOrder {


  public static void main1(String[] args) {
    ExecutorService pool = Executors.newSingleThreadExecutor();
    pool.execute(new Worker("A", "A"));
    pool.execute(new Worker("B", "B"));
    pool.execute(new Worker("C", "C"));
    pool.shutdown();
  }

  //测试join
  public static void main(String[] args) throws Exception {

    final MultiThreadExecuteInOrder multiThreadExecuteInOrder = new MultiThreadExecuteInOrder();
    System.err.println(Thread.currentThread().getName() + "启动");
    Thread t = new Thread(new Runnable() {
      public void run() {
        System.err.println(Thread.currentThread().getName() + "启动");
        try {
          Thread.sleep(1000L);
//                    multiThreadExecuteInOrder.notify();
          System.err.println(Thread.currentThread().getName() + "已通知");
          Thread.sleep(1000);
          System.err.println(Thread.currentThread().getName() + "执行完毕");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }

    });
    t.start();
    //不要下面的等待 整个进程也会等到子线程执行完毕之后才会退出 不符合主线程结束 进程结束的常规认知。。。
//        try {
//
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    multiThreadExecuteInOrder.wait();
    System.err.println(Thread.currentThread().getName() + "执行完毕");
  }

  public static void main11(String[] args) {
    System.err.println(Thread.currentThread().getName() + "启动");
    final CountDownLatch lock = new CountDownLatch(2);

    Thread t = new Thread(new Runnable() {
      public void run() {
        System.err.println(Thread.currentThread().getName() + "启动");
        try {
          Thread.sleep(1000L);
          System.err.println(Thread.currentThread().getName() + "执行完毕");

          lock.countDown();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }

    });
    t.start();
    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = t.getUncaughtExceptionHandler();
    uncaughtExceptionHandler.uncaughtException(t, new RuntimeException("exception on purpose"));
    try {
      lock.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //不要下面的等待 整个进程也会等到子线程执行完毕之后才会退出 不符合主线程结束 进程结束的常规认知。。。
    System.err.println(Thread.currentThread().getName() + "执行完毕");

  }

    /*
     join语义：当前线程等待子线程终止 CountDownLatch也可以实现相同的效果？
     */

  private static class Worker implements Runnable {

    private String name;

    private String content;

    Worker(String name, String content) {
      this.name = name;
      this.content = content;
    }

    public void run() {
      Thread.currentThread().setName(name);
      System.err.println(name + "--->" + content);
    }
  }

  /*
   * CountDownLatch
   * 当前线程等待其他线程执行完毕后 再执行await后面的逻辑
   * 当指定的countDown数量为1时 可作为开关使用 当新线程执行完某个逻辑后 当前线程在继续执行
   *
   * CyclicBarrier
   *
   * ReentrantLock
   * Semaphore
   * 各自的含义 使用场景
   * https://www.jianshu.com/nb/27384241
   */


}


