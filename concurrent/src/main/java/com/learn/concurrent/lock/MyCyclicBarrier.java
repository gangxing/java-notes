package com.learn.concurrent.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/10 19:04
 */

/**
 * 简易版 可重置 公共屏障还未实现 跟JDK的实现方案完全是两回事
 */
public class MyCyclicBarrier {

  static Logger logger = LoggerFactory.getLogger(MyCyclicBarrier.class);

  private final Sync sync;

  public MyCyclicBarrier(int parties) {
    sync = new Sync(parties);
  }

  public void await() {
    sync.await();
  }

  private static class Sync extends AbstractQueuedSynchronizer {

    public Sync(int parties) {
      setState(parties);
    }

    @Override
    protected int tryAcquireShared(int arg) {
      int c = getState();
      logger.info("state=" + c);
      //每一次await会调两遍 所以不能固定返回-1
      return getState() == 0 ? 1 : -1;
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
      //直接释放
      return true;
    }

    void await() {
      for (; ; ) {
        int nextc = getState() - 1;
        if (nextc < 0) {
          System.err.println("已经不大于0啦");
          return;
        }
        if (compareAndSetState(nextc + 1, nextc)) {
          if (nextc > 0) {
            //将自己加入到队列
            acquireShared(1);
          } else {
            releaseShared(1);
          }
          return;
        }
        //尝试减1 如果大于0
      }
    }

  }
}
