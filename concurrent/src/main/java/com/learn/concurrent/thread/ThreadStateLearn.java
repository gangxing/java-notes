package com.learn.concurrent.thread;

import java.util.logging.Logger;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/9 22:11
 */
public class ThreadStateLearn {

  static Logger logger = Logger.getLogger(ThreadStateLearn.class.getName());


  /**
   * 学习线程状态的转换路径 https://www.cnblogs.com/waterystone/p/4920007.html
   */


  public static void main(String[] args) {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        logState(Thread.currentThread());
        //println是一个同步方法 会影响thread状态 所以用log输出
        logger.info("正在执行");
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
          logger.info("正在执行1");
        }
      }
    }, "mythread");

    logState(thread);

    thread.start();
    while (thread.isAlive()) {
      logState(thread);
    }
    logState(thread);
  }


  private static void logState(Thread thread) {
    logger.info(thread.getName() + "'s state is " + thread.getState());
  }

  private static class InterruptableTask implements Runnable {

    @Override
    public void run() {

      for (int i = 0; i < 100; i++) {
        if (i == 50) {

        }
      }
    }
  }
}
