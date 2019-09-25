package com.learn.concurrent.threadpool;

/**
 * @ClassName Main
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 02/11/2018 13:53
 */
public class Main {

    public static void main(String[] args) {

//        for (int c=0;c<127;c++){
//            System.err.println(c+"="+(char)c);
//        }
        long max=1L<<62;
        System.err.println(max);

        long rate=1024 * 1024 *1024;

        System.err.println(max/rate);
    }
}
