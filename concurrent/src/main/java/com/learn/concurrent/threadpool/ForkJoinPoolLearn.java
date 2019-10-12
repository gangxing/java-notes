package com.learn.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/11 11:50
 */

/**
 * https://blog.csdn.net/tyrroo/article/details/81390202
 */
@Slf4j
public class ForkJoinPoolLearn {

    public static void main(String[] args) {
        ForkJoinPool pool = initPool();
        for (int i = 0; i < 1000; i++) {
            Task task = new Task(String.valueOf(i));
            //mock long time
            Random random = new Random();
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                log.error("I'm interrupted", e);
            }

            log.info(task + " has been created");
//            pool.invoke(task);
        }
    }

    private static ForkJoinPool initPool() {

        ForkJoinPool pool=new ForkJoinPool();

        return  pool;
    }

}
