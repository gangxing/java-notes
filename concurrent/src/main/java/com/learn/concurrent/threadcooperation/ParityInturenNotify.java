package com.learn.concurrent.threadcooperation;

/**
 * @Description 交替输出奇偶数
 * @Author xgangzai
 * @Date 2019/11/12 11:10
 */
public class ParityInturenNotify {

    public static void main(String[] args) {
        Object lock=new Object();
        PrintWorker workerA = new PrintWorker(1,lock);
        PrintWorker workerB = new PrintWorker(2,lock);

        Thread threadA = new Thread(workerA, "jishu");
        Thread threadB = new Thread(workerB, "oushu");

        threadA.start();
        threadB.start();
    }


    private static class PrintWorker implements Runnable {

        private int num;

        private static final int STEP = 2;


        private Object lock;

        public PrintWorker(int num, Object lock) {
            this.num = num;
            this.lock = lock;
        }

        @Override
        public void run() {

            while (true) {

                synchronized (lock) {
                    System.err.println(tName() + "-" + num);
                    num += STEP;

                    lock.notify();

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        private String tName() {
            return Thread.currentThread().getName();
        }
    }
}
