package com.learn.concurrent.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/9/25 20:29
 */
public class FakeSynchornizeTest {


    public static void main(String[] args) {
        Adder a = new Adder();
        Adder b = new Adder();
        Thread ta = new Thread(() -> {
            for (int i = 0; i < 10000; i++)
                a.add();
        });

        Thread tb = new Thread(() -> {
            for (int i = 0; i < 10000; i++) b.add();
        });
        ta.start();
        tb.start();
        try {

            ta.join();
            tb.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.err.println(Adder.v.intValue());
    }

    private static class Adder {
        static AtomicInteger v = new AtomicInteger(0);

        Class lock=Adder.class;

        public  void add() {
//            synchronized (lock) {
//                v.incrementAndGet();
//            }
            v.incrementAndGet();
        }


    }
}
