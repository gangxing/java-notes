package com.learn.concurrent.threadpool;

import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/11 17:48
 */
public class MyThreadPoolExecutor extends AbstractExecutorService {


  private static final int COUNT_BITS = Integer.SIZE - 3;
  private static final int RUNNING = -1 << COUNT_BITS;
  private static final int SHUTDOWN = 0 << COUNT_BITS;
  private static final int STOP = 1 << COUNT_BITS;
  private static final int TIDYING = 2 << COUNT_BITS;
  private static final int TERMINATED = 3 << COUNT_BITS;
  Set<Worker> workers;
  BlockingQueue<Runnable> taskQueue;
  ThreadFactory threadFactory;

  public MyThreadPoolExecutor(Set<Worker> workers, BlockingQueue<Runnable> taskQueue,
      ThreadFactory threadFactory) {
    this.workers = workers;
    this.taskQueue = taskQueue;
    this.threadFactory = threadFactory;
  }

  public static void main(String[] args) {
    System.err.println("RUNNING=" + RUNNING);
    System.err.println("SHUTDOWN=" + SHUTDOWN);
    System.err.println("STOP=" + STOP);
    System.err.println("TIDYING=" + TIDYING);
    System.err.println("TERMINATED=" + TERMINATED);
  }

  @Override
  public void shutdown() {

  }

  @Override
  public List<Runnable> shutdownNow() {
    return null;
  }

  @Override
  public boolean isShutdown() {
    return false;
  }

  @Override
  public boolean isTerminated() {
    return false;
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return false;
  }

  @Override
  public void execute(Runnable command) {

  }

  private class Worker implements Runnable {

    private Thread thread;

    private Runnable firstTask;

    public Worker(Thread thread, Runnable firstTask) {
      this.thread = thread;
      this.firstTask = firstTask;
    }

    public Thread getThread() {
      return thread;
    }

    public Runnable getFirstTask() {
      return firstTask;
    }

    @Override
    public void run() {
      runLoop();
    }

    private void runLoop() {
      Worker worker = this;
      Runnable task = worker.getFirstTask();
//            if (task!=null ||(taskQueue.take())!=null){
//
//            }
    }
  }
}
