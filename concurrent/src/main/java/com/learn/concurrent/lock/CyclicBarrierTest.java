package com.learn.concurrent.lock;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CyclicBarrierTest
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 31/10/2018 21:50
 */
public class CyclicBarrierTest {

    /*
     * 栅栏
     * 强调的是多个线程在一个公共的屏障点相互等待 直至所有的线程都达到公共屏障点
     * 如果其中某一个线程因为一场、超时、中断等原因离开了屏障点 所有相互等待的线程都会受到BrokenBarrierException
     * 异常而离开公共屏障点 经试验 抛出异常后 已到达屏障点的线程会一直等待
     * 怎么应对这种场景呢
     * 原因是异常在屏障点之前
     * 先测试异常在屏障点之后的场景 其他在屏障点等待的线程会正常执行完屏障点后面的逻辑 然后终止
     *
     * 问题来了 如果线程在到达屏障点之前抛异常了 怎么办 在finally里await()?
     * 各种尝试都不行
     * 采用超时机制？？？ 等所有在屏障点等到知道超时 抛出异常 没有任何线程会执行后面的逻辑或者barrier action
     *
     *
     * 等所有线程都到达公共屏障点后 可以指定执行后续统一的动作
     * 这个动作具体由哪个线程来执行呢？ 最后一个到达公共屏障点的线程
     * This <em>barrier action</em> is useful
     * for updating shared-state before any of the parties continue.
     *
     * 栅栏适用的场景 将一个大任务分成多个小任务 待各个线程执行完自己的任务是 再通过barrier action执行汇总等统一
     * 动作
     * 不太懂 各个线程执行的逻辑放在哪里？？？
     *
     * 可循环作何解释 适用什么场景
     * The barrier is called <em>cyclic</em> because it can be re-used after the waiting threads
     * are released.
     * 所谓可循环使用是指当指定数量的线程达到公共屏障点后 barrier恢复至初始状态
     * 可以再次使用 但是一定要保证到达屏障点的线程数量是barrier设定的数量的整数倍 否则最后一批线程会一直等待
     *
     *
     *
     *
     * P个S
     * 如果进程CPU高 通过top和jstack找到具体的线程 再做分析
     * 首先确认当前进程的pid 可以通过jps（仅限java进程）或者 ps -ef | grep java
     *
     * 再执行top命令 然后执行shift+h 或者H （这个快捷键只在Linux上有效 Mac上无效）
     * 根据CPU百分比或者执行时间两个指标找出可疑的线程 此时前面的PID就是线程ID
     * 然后执行jstack <PID>(进程ID) 由于此时的线程ID（nid）是16进制 所以需要将top界面的
     * PID 转换成16进制 printf 0x%x <PID> -->nid
     *
     *
     *
     */

    public static void main(String[] args) {

        int n = 10;
        CyclicBarrier barrier = new CyclicBarrier(n, new Drive());
        for (int i = 0; i < 3*n; i++) {
            new Thread(new Boarding(barrier)).start();
        }

        //这就是可重复使用？？？
//        for (int i = 0; i < n+1; i++) {
//            new Thread(new Boarding(barrier)).start();
//        }
    }

    private static class Boarding implements Runnable {

        private CyclicBarrier barrier;

        Boarding(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.err.println(tName() + "boarding...");
                int random = new Random().nextInt(100);
                trySleep();

                System.err.println(tName() + "boarded,waiting others...");

                if (random % 2 == 0) {
//                    throw new RuntimeException("故意抛出异常");//抛出运行时异常 其他到达屏障点的线程会一直等待。。。
//                    throw new InterruptedException("故意抛出异常");//抛出中断时异常 其他到达屏障点的线程会一直等待。。。
                }
//                barrier.await(1, TimeUnit.MINUTES);//公共屏障点
                barrier.await();
                System.err.println(tName() + "all boarded");
                System.err.println(tName() + "正常执行完逻辑");
            } catch (Exception e) {
                e.printStackTrace();
//                int waitingCount = barrier.getNumberWaiting();
//                if (waitingCount > 0 && !barrier.isBroken()) {
//                    try {
//                        barrier.await();//如果栅栏还没有broken的话并且还有等待的线程 则一定要保证自己也达到屏障点 否则其他线程会一直等待
//                    }catch (Exception e1){
//                        e1.printStackTrace();
//                    }
//                    Thread.currentThread().interrupt();
//                }
            }

        }

        private String tName() {
            return Thread.currentThread().getName();
        }

        private void trySleep() {
            try {
                Thread.sleep(new Random().nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Drive implements Runnable {
        @Override
        public void run() {
            System.err.println(tName() + "driving");
        }

        private String tName() {
            return Thread.currentThread().getName();
        }
    }
}
