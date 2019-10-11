package com.learn.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/11 19:29
 */
@Slf4j
public class Task implements Runnable {

    private String name ;

    public Task(String name) {
        this.name = name;
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
                name ;
    }
}
