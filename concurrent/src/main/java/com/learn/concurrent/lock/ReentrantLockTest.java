package com.learn.concurrent.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockTes
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 30/10/2018 21:54
 */
public class ReentrantLockTest {

    /*
     *
     * AQS
     * exclude mode
     * share mode
     *
     * 公平锁：讲究一个先来后到 线程获取锁的顺序和调用lock的顺序一致 FIFO
     * 非公平锁：获取锁的顺序随机 （和调用lock的顺序无关）
     * ReentrantLock 默认使用非公平锁 提高性能 因为不会对队列的数据处理
     *
     * CAS Compare and Swap
     * https://blog.csdn.net/ls5718/article/details/52563959
     * 是jdk concurrent包实现的基石（+volatile）
     * 读-修改-写操作
     *
     * CAS 存在的问题：
     * 1.aba  解决方案：加版本号 AtomicStampedReference
     * 2.时间开销大 不断地修改 比较 直至成功
     * 3.不能对多个原子变量
     *
     *
     *
     * 独占锁 悲观锁
     * 共享锁 乐观锁
     *
     * 为什么volatile不能保证原子性
     * volatile 既然保证了可见性 所以任何线程在修改的时候 读到的已经是最新的值 为什么就不能保证原子性了呢
     *
     * 不要将volatile用在getAndOperate场合，仅仅set或者get的场景是适合volatile的
     * https://www.cnblogs.com/Mainz/p/3556430.html
     *
     *
     *
     */

    /*
     * 可重入锁
     * 功能：同步锁 同synchronized 在jdk1.5 性能明显优于后者 但在1.6里 synchronized优化后 两者的性能相差不多
     * 但是ReentrantLock有更高级 更丰富的功能
     *
     * 可重入 多次获取 但是释放锁的次数一定要达到获取锁的次数 才能真正释放掉锁 其他线程才有机会获取锁
     * 可中断 如果获取不到锁 中断当前线程 这样可以解决死锁问题 （题外话 既然尝试解决死锁问题 为什么不从根源上
     * 避免死锁的出现呢 妈的智障）lockInterruptibly
     * 可限时 tryLock
     * 公平锁 (默认是非公平锁) 但是可以指定为公平锁
     *
     *
     */

    private static int count = 0;

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2000);
        Lock lock = new ReentrantLock(true);

        for (int i = 0; i < 2000; i++) {
            new Thread(new Worker(latch, lock)).start();
            latch.countDown();
        }

    }

    private static class Worker implements Runnable {


        private CountDownLatch latch;

        private Lock lock;

        Worker(CountDownLatch latch, Lock lock) {
            this.latch = latch;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                for (; ; ) {
                    boolean acquired = lock.tryLock();
                    if (acquired) {

                        String name = Thread.currentThread().getName();
                        System.err.println(name + "before=" + count);
                        for (int i = 0; i < 100; i++) {
                            count++;
                        }
                        System.err.println(name + "after=" + count);
                        return;


                    }
                }
            } finally {
                lock.unlock();
            }

//            while (lock.tryLock()) {
//                try {
//                    String name = Thread.currentThread().getName();
//                    System.err.println(name + "before=" + count);
//                    for (int i = 0; i < 100; i++) {
//                        count++;
//                    }
//                    System.err.println(name + "after=" + count);
//                    return;
//
//
//                } finally {
//                    lock.unlock();
//                }

//            }
        }
    }


}
