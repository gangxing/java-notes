package com.learn.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/10 09:29
 */
//@Slf4j
public class ThreadInterruptLearn {

    static Logger log = Logger.getLogger(ThreadInterruptLearn.class.getSimpleName());

    /**
     * 线程中断学习
     * https://www.infoq.cn/article/java-interrupt-mechanism
     * https://blog.csdn.net/canot/article/details/51087772
     * <p>
     * <p>
     * Java的中断是什么
     * 是Java多线程间一种协作方式，每个线程有一个interrupt标识位。一个线程可以通过设置目标线程的标志位
     * 来告知目标线程需要中断。目标线程通过轮询自己的标志位是否为true(调用thread#isInterrupted())来判断
     * 自己是否被别人发起了中断请求
     * <p>
     * 有什么用
     * 多线程间的一种协作方式
     * <p>
     * <p>
     * 实现机制（和CPU中断有什么关联，CPU时间片，任务调度和中断？？还是看不懂，算了先不管了底层的了）
     * https://segmentfault.com/a/1190000017457234
     * <p>
     * 中断标识和InterruptException关系
     * 在前面讲到目标线程是通过轮询自己的中断标识位来判断自己是否被其他线程请求中断，但是当目标线程处于等待
     * 状态时（sleep wait join），被CPU挂起，则有JVM层面？来判断标识位并抛出InterruptException,打破等待
     * 状态，让目标线程获得CPU执行时间片。以此达到跟自己轮询标识位一样的效果。所以，InterruptException存在的
     * 意义是当源线程处于等待状态，没办法主动轮询标识位这种场景而存在的
     * <p>
     * 标识位的转换路径
     * 初始状态为false，被其他线程设置为true，自己轮询thread#interrupted()会重置为false，
     * 抛出InterruptedException后会重置为false
     * <p>
     * <p>
     * 会在哪些场景下响应中断
     * 线程在等待状态
     * <p>
     * 在哪些场景下不会响应中断
     * <p>
     * 使用示例
     * 比如线程a想要给线程b发送一个中断，线程b来响应
     * 在a中调用b.interrupt()即可
     * 至于b怎样才能感知标志位被设置了
     * 1.b主动轮询,在b线程中Thread.currentThread().isInterrupted()。如果为true，表示收到了中断请求
     * 2.b线程在调用sleep,a.join(等待a线程结束).x.wait等进入等待状态后，不会被CPU执行，所以jvm层处理了。
     * 收到中断请求后，向上抛出中断异常，以此来唤醒b线程重新获得CPU时间片
     *
     * interrupt作为线程间的一种协作方式，本质上不会改变线程的生命周期，但响应线程收到中断请求后的处理是预先
     * 定义好的。从这一层面来看，interrupt就是一种线程间通讯方式而已。
     * <p>
     * 后记 和Future的cancel有什么关系？
     */

    public static void mainInterruptChild() {

        //主线程中断子线程，子线程响应中断
        Thread interruptableThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!Thread.currentThread().isInterrupted()) {
                        log.info("I'm running");
                    } else {
                        log.info("I'm interrupted and going to exit");
                        break;
                    }
                }

            }
        });



        interruptableThread.start();

        for (int i = 0; i < 100; i++) {
            log.info("main thread is running");
        }
        log.info("main thread is going to interrupt child thread");
        interruptableThread.interrupt();

    }

    public static void main(String[] args) {
        childInterruptMain();
    }

    //主线程等待子线程运行完毕，但是子线程可能耗时长，然后中断主线程的等待
    public static void childInterruptMain() {
        Thread caller = Thread.currentThread();
        Thread interruptableChild = new Thread(new LongTimeTask(caller));
        interruptableChild.start();
//
//        Thread noninterruptableChild = new Thread(new LongTimeTaskNonInterrupt(caller));
//        noninterruptableChild.start();
//        try {
//            noninterruptableChild.join();
//        } catch (InterruptedException e) {
//            //这时标识位已经被复原了，如果想要继续抛出去，需要再次设置为true
//            log.info("call flag" + caller.isInterrupted());
//            e.printStackTrace();
//        }
        for (int i=0;i<Integer.MAX_VALUE;i++){
            System.err.println("times"+i);
        }

    }

    private static class LongTimeTask implements Runnable {

        private Thread callerThread;

        public LongTimeTask(Thread callerThread) {
            this.callerThread = callerThread;
        }

        @Override
        public void run() {
            log.info("I'm staring run");

            //mock long time

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                log.info("" + Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }

            log.info("too long to run, cancel caller wait");

            callerThread.interrupt();

            //继续执行
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.info("" + Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }

            log.info("finally, run to the end ");
        }
    }

    private static class LongTimeTaskNonInterrupt implements Runnable {

        private Thread callerThread;

        public LongTimeTaskNonInterrupt(Thread callerThread) {
            this.callerThread = callerThread;
        }

        @Override
        public void run() {
            log.info("I'm staring run");

            //mock long time
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.info("" + Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }


            log.info("finally, run to the end ");


        }
    }


}
