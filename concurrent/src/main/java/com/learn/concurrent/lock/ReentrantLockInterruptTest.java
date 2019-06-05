package com.learn.concurrent.lock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockInterruptTest
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 30/10/2018 23:09
 */
public class ReentrantLockInterruptTest extends Thread {


    public static void main(String[] args) {

        ReentrantLockInterruptTest test1 = new ReentrantLockInterruptTest(1, "test1");
        ReentrantLockInterruptTest test2 = new ReentrantLockInterruptTest(2, "test2");
        test1.start();
        test2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DeadLockChecker.check();
    }

    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();

    int lock;

    public ReentrantLockInterruptTest(int lock, String name) {
        super.setName(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                trySleep();
                lock2.lockInterruptibly();
                trySleep();
            }else {
                lock2.lockInterruptibly();
                trySleep();
                lock1.lockInterruptibly();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.err.println(tName() + "退出");
        }
    }

    private void trySleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String tName() {
        return Thread.currentThread().getName();
    }

    static class DeadLockChecker {
        private final static ThreadMXBean MX_BEAN = ManagementFactory.getThreadMXBean();

        public static void check() {
            Thread tt = new Thread(() -> {
                while (true) {
                    long[] deadLockThreadIds = MX_BEAN.findDeadlockedThreads();
                    if (deadLockThreadIds != null) {
                        ThreadInfo[] threadInfos = MX_BEAN.getThreadInfo(deadLockThreadIds);
                        for (Thread t : Thread.getAllStackTraces().keySet()) {
                            for (int i = 0; i < threadInfos.length; i++) {
                                if (t.getId() == threadInfos[i].getThreadId()) {
                                    System.err.println(t.getName());
                                    t.interrupt();
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            tt.setDaemon(true);
            tt.start();
        }

    }
}
