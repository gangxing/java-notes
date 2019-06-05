package com.learn.concurrent.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName ThreadPoolExecutorTest
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 05/11/2018 09:57
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
//        ThreadPoolExecutor poolExecutor=new ThreadPoolExecutor(6);
    }


    private static class MyThreadFactory implements ThreadFactory{

        @Override
        public Thread newThread(Runnable r) {
            return null;
        }
    }
}
