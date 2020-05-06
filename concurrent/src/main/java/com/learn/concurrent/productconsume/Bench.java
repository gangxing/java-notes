package com.learn.concurrent.productconsume;

import java.util.LinkedList;
import java.util.Random;

/**
 * @ClassName Bench
 * @Description TODO https://juejin.im/entry/596343686fb9a06bbd6f888c
 * @Author xianjun@ixiaopu.com
 * @Date 30/10/2018 00:26
 */
public class Bench {

  private static LinkedList<Integer> bench = new LinkedList<>();


  public static void main(String[] args) {
    new Thread(new Producer()).start();
    new Thread(new Producer()).start();
    new Thread(new Consumer()).start();
    new Thread(new Consumer()).start();

  }

  private static class Producer implements Runnable {


    @Override
    public void run() {
      while (true) {
        synchronized (bench) {
          if (bench.size() >= 5) {
            try {
              System.err.println("满了 等待");
              bench.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } else {
            int i = new Random().nextInt();
            bench.offer(i);
            try {
              Thread.sleep(500);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            System.err.println(Thread.currentThread().getName() + "产生了" + i);
            bench.notify();
          }
        }
      }

    }
  }

  private static class Consumer implements Runnable {

    @Override
    public void run() {
      while (true) {
        synchronized (bench) {
          if (bench.size() <= 0) {
            try {
              System.err.println("空了 等待");
              bench.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } else {
            int i = bench.poll();
            try {
              Thread.sleep(500);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            System.err.println(Thread.currentThread().getName() + "消费了" + i);
            bench.notify();
          }
        }
      }
    }
  }

}
