package com.learn.concurrent;

/**
 * @ClassName ThreadLocalLearn
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 11/11/2018 20:20
 */
public class ThreadLocalLearn {


    public static void main(String[] args) {
        ThreadLocal<Integer> local=new ThreadLocal<>();
        local.set(4);

        Integer i=local.get();
        System.err.println(i);
    }

}
