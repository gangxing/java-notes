package com.learn.webdemo.config;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 线程池配置
 * @author: xinggang
 * @create: 2020-02-12 15:55
 */
@Log4j2
@Configuration
public class ThreadPoolConfig {

  private UncaughtExceptionHandler exceptionHandler = new ExceptionLogHandler();

  /**
   * 通知线程池
   *
   * @return 线程池
   */
  @Bean
  public ExecutorService fanPool() {
    return newPool(140,
        200,
        0,
        "Fan Thread-",
        new CallerRunsPolicy());
  }




  private ExecutorService newPool(int size, int capacity, long keepAliveTime, String namePrefix,
      RejectedExecutionHandler handler) {
    return new ThreadPoolExecutor(size,
        size,
        keepAliveTime,
        TimeUnit.SECONDS,
        new LinkedBlockingDeque<>(capacity),
        new NamedThreadFactory(namePrefix, exceptionHandler), handler);
  }

  /**
   * 自定义线程异常处理器（目前只打印日志）
   */
  private static class ExceptionLogHandler implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
      log.error("Exception occur: ", e);
    }
  }

  /**
   * 指定名称的线程工程
   */
  private static class NamedThreadFactory implements ThreadFactory {

    private String prefix;

    private UncaughtExceptionHandler uncaughtExceptionHandler;

    private AtomicInteger count = new AtomicInteger(1);

    private NamedThreadFactory(String prefix,
        UncaughtExceptionHandler uncaughtExceptionHandler) {
      this.prefix = prefix;
      this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public Thread newThread(Runnable r) {
      Thread t = new Thread(r, prefix + count.getAndIncrement());
      t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
      return t;
    }
  }


}
