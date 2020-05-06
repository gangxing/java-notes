package com.learn.concurrent.threadcooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 多个线程ABCD，按顺序各自输出ABCD
 * @Author xgangzai
 * @Date 2019/11/12 11:49
 */
public class PrintInOrderThreadPool {

  public static void main(String[] args) throws Exception {
    Thread A = new Thread(new Printer("A"), "A");
    Thread B = new Thread(new Printer("B"), "B");
    Thread C = new Thread(new Printer("C"), "C");
    Thread D = new Thread(new Printer("D"), "D");
    //这种实现方式只有一个线程
    ExecutorService pool = Executors.newSingleThreadExecutor();
//        pool.submit(A);
//        pool.submit(B);
//        pool.submit(C);
//        pool.submit(D);
    pool.execute(A);
    pool.execute(B);
    pool.execute(C);
    pool.execute(D);
    pool.shutdown();
  }


  private static class Printer implements Runnable {

    private String s;

    public Printer(String s) {
      this.s = s;
    }

    @Override
    public void run() {

      System.err.println(tName() + " " + s);

    }

    private String tName() {
      return Thread.currentThread().getName();
    }
  }
}
