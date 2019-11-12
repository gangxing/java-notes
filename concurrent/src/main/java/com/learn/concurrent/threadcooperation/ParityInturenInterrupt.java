package com.learn.concurrent.threadcooperation;

/**
 * @Description 交替输出奇偶数
 * @Author xgangzai
 * @Date 2019/11/12 11:10
 */
public class ParityInturenInterrupt {

    public static void main(String[] args) {
        PrintWorker workerA = new PrintWorker(1);
        PrintWorker workerB = new PrintWorker(2);

        Thread threadA = new Thread(workerA, "jishu");
        Thread threadB = new Thread(workerB, "oushu");
        workerA.setOther(threadB);
        workerB.setOther(threadA);

        threadA.start();
        threadB.start();
    }


    private static class PrintWorker implements Runnable {

        private int num;

        private static final int STEP = 2;


        private Thread other;

        public PrintWorker(int num) {
            this.num = num;
        }

        public void setOther(Thread other) {
            this.other = other;
        }

        @Override
        public void run() {

            while (true) {

                System.err.println(tName() + "-" + num);
                num += STEP;

                other.interrupt();

                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    //开始干活啦
                }
            }
        }

        private String tName() {
            return Thread.currentThread().getName();
        }
    }
}
