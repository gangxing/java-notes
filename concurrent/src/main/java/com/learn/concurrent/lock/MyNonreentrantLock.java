package com.learn.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/9 14:16
 */
public class MyNonreentrantLock implements Lock, java.io.Serializable {
    private static final long serialVersionUID = 7373984872572414699L;

    private final Sync sync;

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            int c = getState();
            //如果已经被持有了，立即返回false
            if (c != 0) {
                //有可能是自己获取的,这时如果不做额外处理的话，会阻塞在这里，然而第一次获取的永远二释放不了了
                //整个逻辑就会无限阻塞
                if (Thread.currentThread() == getExclusiveOwnerThread()) {
                    throw new IllegalMonitorStateException("重复获取了");
                }
                return false;
            }
            //尝试原子更新state为1
            if (compareAndSetState(0, arg)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            //arg always is 1
            int c = getState() - arg;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            if (c == 0) {
                //这时候不用原子更新，是因为只会由当前持有者线程来调用
                setExclusiveOwnerThread(null);
                setState(c);
                return true;
            }
            return false;
        }

        protected boolean tryAcquireNonException(int arg) {
            int c = getState();
            //如果已经被持有了，立即返回false
            if (c != 0) {
                //有可能是自己获取的,这时如果不做额外处理的话，会阻塞在这里，然而第一次获取的永远二释放不了了
                //整个逻辑就会无限阻塞
                return false;
            }
            //尝试原子更新state为1
            if (compareAndSetState(0, arg)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

    }

    public MyNonreentrantLock() {
        this.sync = new Sync();
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireNonException(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }


}
