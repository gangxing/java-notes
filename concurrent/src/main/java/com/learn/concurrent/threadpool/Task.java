package com.learn.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/11 19:29
 */
@Slf4j
class Task implements Runnable {

  private String name;

  public Task(String name) {
    this.name = name;
  }

  public static void main(String[] args) {
    Task task = new Task("hahah");
    log.info("task is {}", task);
  }

  @Override
  public void run() {
    log.info(this + " is running");
//            doBiz();
    log.info(this + " done");
  }

  @Override
  public String toString() {
    return "Task-" +
        name;
  }
}
