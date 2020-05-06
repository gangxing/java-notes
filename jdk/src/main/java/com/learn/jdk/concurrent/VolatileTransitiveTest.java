package com.learn.jdk.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/20 12:24
 */
@Slf4j
public class VolatileTransitiveTest {

  int a = 0;

  boolean b = false;

  //A线程写的a对B线程不可见


  public static void main(String[] args) {
    VolatileTransitiveTest test = new VolatileTransitiveTest();
    Thread a = new Thread(() -> {
      test.a = 50;
      test.b = true;
      log.info("a done");
    });

    Thread b = new Thread(() -> {

      for (int i = 0; i < 1000; i++) {
//                if (test.b && test.a!=50){
        log.info("a=" + test.a + "; b=" + test.b);
//                }
      }


    });

    b.start();

    a.start();

  }
}
