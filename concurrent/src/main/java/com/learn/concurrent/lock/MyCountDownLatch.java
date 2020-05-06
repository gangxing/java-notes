package com.learn.concurrent.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/10 16:13
 */
public class MyCountDownLatch {

  private final Sync sync;


  public MyCountDownLatch(int permits) {
    this.sync = new Sync(permits);
  }

  public void await() throws InterruptedException {
    sync.acquireSharedInterruptibly(1);
  }

  public void countDown() {
    sync.releaseShared(1);
  }

  private static class Sync extends AbstractQueuedSynchronizer {

    public Sync(int permits) {
      //预定义
      setState(permits);
    }

    @Override
    protected int tryAcquireShared(int arg) {
      //0表示没有线程持有锁，不是0时返回负数，将当前线程加入到队列中等待 直至state为0时，全部被唤醒
      return getState() == 0 ? 1 : -1;

    }

    @Override
    protected boolean tryReleaseShared(int arg) {
      //检查是否大于1 减1 如果等于1 则返回true
//            自旋+CAS更新state
      for (; ; ) {
        int c = getState();
        if (compareAndSetState(c, c - arg)) {
          return c == 1;
        }
      }
    }
  }


}
