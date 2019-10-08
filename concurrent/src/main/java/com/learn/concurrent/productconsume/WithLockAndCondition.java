package com.learn.concurrent.productconsume;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 花了一个下午终于调通了
 * 关键在于真正更新队列和count前要再次校验是否符合条件
 * 可能是先阻塞，满足条件后被唤醒，但到自己执行时，条件又被其他生产(或消费)线程破坏了
 */
public class WithLockAndCondition {


    private ReentrantLock lock = new ReentrantLock(true);
    //生产者需要的条件:不满,如果满了就等待
    private Condition notFull = lock.newCondition();

    //消费者需要的条件:非空,如果空了就等待
    private Condition notEmpty = lock.newCondition();

    private ExecutorService producerPool = Executors.newFixedThreadPool(5, new ThreadFactory() {

        AtomicInteger num = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("Producer-" + num.getAndIncrement());
            return t;
        }
    });

    private ExecutorService consumerPool = Executors.newFixedThreadPool(5, new ThreadFactory() {
        AtomicInteger num = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("Consumer-" + num.getAndIncrement());
            return t;
        }
    });

    private static final int CAPACITY = 8;

    private int count = 0;

    private Integer[] list = new Integer[CAPACITY];


    public static void main(String[] args) {
        WithLockAndCondition app = new WithLockAndCondition();
        new Thread(() -> {
            while (true) {
                app.producerPool.execute(app.new Producer());
                app.sleep();
            }

        }).start();

        new Thread(() -> {
            while (true) {
                app.consumerPool.execute(app.new Consumer());
                app.sleep();
            }
        }).start();
    }

    private void sleep() {
        try {
            Random random = new Random();
            int time = random.nextInt(1000);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Producer implements Runnable {
        @Override
        public void run() {
            if (lock.tryLock()) {
                try {
                    if (count >= CAPACITY) {
                        try {
                            print("满了 等待");
                            //已经满了
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    //这里一定要再次校验，因为如果是先等待，再进入这里，只能说明曾经满足过条件，但现在有可能被其他
                    //生产者填满了
                    if (count < CAPACITY) {
                        int i = 1 + new Random().nextInt(1000);
                        list[count] = i;
                        sleep();
                        print("产生了" + i + "到" + count);
                        count++;
                        //已经不为空了，通知消费者可以消费了（如果有消费者在等待）
                        notEmpty.signalAll();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private class Consumer implements Runnable {
        @Override
        public void run() {
            if (lock.tryLock()) {
                try {
                    if (count <= 0) {
                        try {
                            print("空了 等待");
                            //需要的是非空，但是已经空了 所以等待非空为止
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    //这里还要进行第二次判断，因为被唤醒只能说明曾经是有元素的，有可能现在已经被其他消费者消费了
                    if (count > 0) {
                        count--;
                        int i = list[count];
                        sleep();
                        print("消费了" + i + "在" + count);
                        //已经满足了不满的条件，通知生产者可以生产了（如果有生产者在等待）
                        notFull.signalAll();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private void print(String s) {
        System.err.println(Thread.currentThread().getName() + ":" + s);
    }

}
