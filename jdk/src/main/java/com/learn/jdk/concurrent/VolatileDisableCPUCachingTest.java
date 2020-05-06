package com.learn.jdk.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/20 11:08
 */
@Slf4j
public class VolatileDisableCPUCachingTest {

  int x = 0;
  boolean run = true;

  public static void main(String[] args) {
    VolatileDisableCPUCachingTest test = new VolatileDisableCPUCachingTest();

    new Thread(() -> {
      while (test.run) {
        //只是加这一句是读不到的
        test.x++;

        //加了这一句 也能立即读到
//                log.info("running");

        //加了这一句 也能立即读到
//                log.info("x="+test.x);

      }
      log.info("run end");
    }).start();

    new Thread(() -> {

      for (int i = 0; i < 100; i++) {
        log.info("x=" + test.x);
      }
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

      test.run = false;
      log.info("run false");

      for (int i = 0; i < 100; i++) {
        log.info("x=" + test.x);
      }

    }).start();


  }
}
