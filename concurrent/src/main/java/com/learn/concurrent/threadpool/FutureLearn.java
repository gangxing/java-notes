package com.learn.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/12 18:30
 */
@Slf4j
public class FutureLearn {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        //内部完全调用execute方法的 在哪里将结果放到FutureTask中的呢？？？
        Future<Integer> future=pool.submit(new Task(1,2));

        try {

            Integer result=future.get();
            log.info("result is "+result);
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException ee){
            ee.printStackTrace();
        }

    }

    private static class Task implements Callable<Integer> {

        private Integer a;

        private Integer b;

        public Task(Integer a, Integer b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Integer call() throws Exception {
            Random random = new Random();
            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));

            return a + b;
        }
    }
}
