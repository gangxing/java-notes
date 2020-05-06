package com.learn.concurrent.lock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/10 14:12
 */
public class SemaphoreTest {

  static org.slf4j.Logger log = LoggerFactory.getLogger(SemaphoreTest.class);

//    static Logger log = Logger.getLogger(SemaphoreTest.class.getSimpleName());

  public static void main(String[] args) {
    Semaphore semaphore = new Semaphore(2);
    log.info("permits" + semaphore.availablePermits());
    semaphore.release(4);
    log.info("permits" + semaphore.availablePermits());

    ExecutorService pool = Executors.newFixedThreadPool(10);

    for (int i = 0; i < 100; i++) {
      pool.execute(new Task(semaphore));
    }
    log.info("I'm done");

  }

  private static class Task implements Runnable {

    Semaphore semaphore;

    public Task(Semaphore semaphore) {
      this.semaphore = semaphore;
    }

    @Override
    public void run() {
      log.info("I'm going to acquire");
      try {
        //如果获取的次数达到上限，这里会被阻塞
        //这里release 仅仅只是一个加数的操作？？
        //所以在使用上一定要先acquire 即减1 然后release + 1

        semaphore.acquire();
        log.info("I'm running");

        try {
          doBiz();
          log.info("I'm done");
        } finally {
          semaphore.release();
        }

      } catch (InterruptedException e) {
        e.printStackTrace();
      }


    }

    private void doBiz() {

      Random random = new Random();
      if (random.nextBoolean()) {
//                throw new RuntimeException("我遇到异常啦");
      }

      try {
        TimeUnit.MILLISECONDS.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
