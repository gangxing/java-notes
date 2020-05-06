package com.learn.datastructure.map;

import com.learn.datastructure.util.StringRandoms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import org.apache.commons.lang3.RandomUtils;

/**
 * @ClassName HashMapLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/8/2 00:29
 */
public class ConcurrentHashMapLearn {

  public static void main(String[] args) {
    ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
    for (int i = 0; i < 18; i++) {
      map.put(i, "value" + i);
      String value = map.get(i);
      System.err.println(value);
    }
    map.size();
  }

  public static void main1(String[] args) {

    HashMap<String, String> map = new HashMap<String, String>();
    int threadCount = 10;
    CountDownLatch countDownLatch = new CountDownLatch(threadCount);
    Thread.UncaughtExceptionHandler handler = new MyHandler(countDownLatch);

    List<Thread> treads = new ArrayList<>();
    for (int i = 1; i < threadCount + 1; i++) {
      Thread put = new Thread(new PutWorker(map, i, countDownLatch));
      put.setName("Put thread-" + i);
      put.setUncaughtExceptionHandler(handler);
      treads.add(put);

      Thread get = new Thread(new GetWorker(map));
      get.setName("Get thread-" + i);
      get.setUncaughtExceptionHandler(handler);
//            treads.add(get);
    }

    for (Thread t : treads) {
      t.start();
    }

    try {

      countDownLatch.await();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.err.println(map.size());

  }

  private static class PutWorker implements Runnable {

    private HashMap<String, String> map;


    private int step;

    private CountDownLatch countDownLatch;

    public PutWorker(HashMap<String, String> map, int step, CountDownLatch countDownLatch) {
      this.map = map;
      this.step = step;
      this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
      String tName = Thread.currentThread().getName();
      for (int i = 0; i < 5000; i++) {
        int length = RandomUtils.nextInt(1, 20);
        String s = StringRandoms.random(length);
        String key = tName + s + i;
        map.put(key, s);

      }
      System.err.println(Thread.currentThread().getName() + " put finished");
      countDownLatch.countDown();
    }
  }


  private static class GetWorker implements Runnable {

    private HashMap<String, String> map;

    public GetWorker(HashMap<String, String> map) {
      this.map = map;
    }

    @Override
    public void run() {
      String tName = Thread.currentThread().getName();
      for (int i = 0; i < 500000; i++) {
//                Integer value = map.get(i);
//                if (value != null && !value.equals(i)) {
//                    System.err.println(tName + " get " + i + "->" + value);
//                }
      }
      System.err.println(Thread.currentThread().getName() + " get finished");
    }
  }

  private static class MyHandler implements Thread.UncaughtExceptionHandler {

    CountDownLatch countDownLatch;

    public MyHandler(CountDownLatch countDownLatch) {
      this.countDownLatch = countDownLatch;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
      System.err.println(t.getName() + " exception:" + e.getMessage());
      e.printStackTrace();
      countDownLatch.countDown();
    }
  }
}
