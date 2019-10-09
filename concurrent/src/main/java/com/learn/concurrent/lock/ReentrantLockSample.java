package com.learn.concurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/9 09:29
 */
public class ReentrantLockSample {

    private ReentrantLock lock = new ReentrantLock();

    private ExecutorService pool = Executors.newFixedThreadPool(3, new ThreadFactory() {
        AtomicInteger num = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("MyRun-" + num.getAndIncrement());
            return t;
        }
    });


    public static void main(String[] args) {
        ReentrantLockSample app = new ReentrantLockSample();
        for (int i = 0; i < 10; i++) {
            /*
             * 只有一个线程获得一次执行机会，然后所有线程都会被阻塞
             * 主线程已经退出了,但是jvm还未退出，
             * https://juejin.im/post/5d3188e26fb9a07ef81a374e
             * 因为jvm的退出要等所有的非守护线程执行完毕后才会退出
             */
            app.pool.execute(app.new MyRun());
            //这种方式会在那个唯一获取到锁的线程执行完之后 jvm退出，问题：
            //其他在获取锁时被阻塞的线程算是执行完了还是未执行完,因为tryLock会立即返回，所以是执行完了
            //跟上面线程池有什么区别
            /*
             * TODO 新的问题来了
             * 线程池中的线程是怎么保活的？？在等待什么？？？所以才没有结束？
             * 果然是，线城池内部，每个线程从阻塞队列中获取任务，如果队列为空，则被阻塞（典型的生产者消费者模式）
             *
             */
//            new Thread(app.new MyRun()).start();
        }
        app.print("执行完毕");

    }

    private class MyRun implements Runnable {
        @Override
        public void run() {
            print("尝试获取锁");
            //这里如果获取不到锁，就会立即返回，线程执行完毕
//            if (lock.tryLock()) {
            //这种方式，如果锁被其他线程持有，才会阻塞等待

            /*
              任何线程获取锁的时候，锁有三种状态
              1.没有被持有
              2.被当前线程自己持有
              3.被其他线程持有

              tryLock和lock两个接口根据这三种场景处理的逻辑有所区分
              对于tryLock
              a.尝试原子更新状态 0->1 如果成功说明是场景1，获取锁成功，并将自己设为这把锁的持有者，返回true
              b.如果原子更新失败，则判断锁的持有者是否是当前线程自己，如果是自己持有则是场景2，增加计数，返回true
              c.以上条件都不满足，说明锁已经被其他线程持有，返回false

              对于lock
              a.尝试原子更新，0->如果更新成功，获取锁成功，将自己设为这把锁的持有者，方法结束
              b.如果原子更新失败，则调用tryLock再获取一遍，如果获取到了，方法结束
              c.再次获取不到，则尝试将自己加入等待队列，如果加入队列失败（什么场景下会失败？？）
              如果b c 两个操作都不成功，则自己中断自己

             */

            lock.lock();
                try {
                    print("获得锁，执行中...");
                    sleep();
                } finally {

                    /*
                     * unlock逻辑
                     * 比较简单
                     * a.校验锁的持有者线程是否是当前线程，如果不是，则抛出异常
                     * b.将state减1，如果减1后的state等于0，则说明锁没有再被持有了，将持有者线程置空
                     * c.更新state
                     * d.如果state等于0，说明当前线程已经释放锁完毕，处理唤醒其他阻塞线程工作
                     *
                     * 线程的阻塞和唤醒是依赖于LockSupport中的park和unpark实现的
                     * LockSupport.park()和unpark()，与object.wait()和notify()的区别？
                     * 主要的区别应该说是它们面向的对象不同。阻塞和唤醒是对于线程来说的，
                     * LockSupport的park/unpark更符合这个语义，以“线程”作为方法的参数，
                     * 语义更清晰，使用起来也更方便。而wait/notify的实现使得“阻塞/唤醒对线程本身来说是被动的，
                     * 要准确的控制哪个线程、什么时候阻塞/唤醒很困难， 要不随机唤醒一个线程（notify）要不唤醒所有的（notifyAll）。
                     *
                     *
                     */
                    lock.unlock();
                    print("执行完毕，释放锁");
                }
//            }

        }
    }

    private void print(String s) {
        System.err.println(Thread.currentThread().getName() + ":" + s);
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

