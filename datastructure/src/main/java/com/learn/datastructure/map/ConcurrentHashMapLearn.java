package com.learn.datastructure.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConcurrentHashMapLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/8/2 00:29
 */
public class ConcurrentHashMapLearn {

    public static void main(String[] args) {

        Thread.UncaughtExceptionHandler handler = new MyHandler();

        HashMap<Integer, Integer> map = new HashMap<>();
        int threadCount = 50;
        List<Thread> treads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            Thread put = new Thread(new PutWorker(map));
            put.setName("Put thread-" + i);
            put.setUncaughtExceptionHandler(handler);
            treads.add(put);


            Thread get = new Thread(new GutWorker(map));
            get.setName("Get thread-" + i);
            get.setUncaughtExceptionHandler(handler);
            treads.add(get);
        }

        for (Thread t : treads) {
            t.start();
        }

    }

    private static class PutWorker implements Runnable {
        private HashMap<Integer, Integer> map;

        public PutWorker(HashMap<Integer, Integer> map) {
            this.map = map;
        }

        @Override
        public void run() {
            String tName = Thread.currentThread().getName();
            for (int i = 0; i < 500000; i++) {
                map.put(i, i);

//                System.err.println(tName + " put " + i);
            }
            System.err.println(Thread.currentThread().getName() + " put finished");
        }
    }


    private static class GutWorker implements Runnable {
        private HashMap<Integer, Integer> map;

        public GutWorker(HashMap<Integer, Integer> map) {
            this.map = map;
        }

        @Override
        public void run() {
            String tName = Thread.currentThread().getName();
            for (int i = 0; i < 500000; i++) {
                Integer value = map.get(i);
                if (value != null && !value.equals(i)) {
                    System.err.println(tName + " get " + i + "->" + value);
                }
            }
            System.err.println(Thread.currentThread().getName() + " get finished");
        }
    }

    private static class MyHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println(t.getName() + " exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
