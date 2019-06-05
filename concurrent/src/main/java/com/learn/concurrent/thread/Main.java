package com.learn.concurrent.thread;

/**
 * @ClassName Main
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 22/11/2018 15:17
 */
public class Main {

    public static void main(String[] args) {
//        exceptionInterrupt();
        System.err.println(tName()+" start");
        long end=System.currentTimeMillis()+10*1000;
        interruptBySelf();
        while (System.currentTimeMillis()<end){
            System.err.println("running ");
        }

        System.err.println(tName()+" end");
    }

    public static void exceptionInterrupt() {
        ExceptionInterrupt thread = new ExceptionInterrupt();
        thread.start();
        try {
            Thread.sleep(2000);
            /*
             * 共用两个线程 main Thread-0
             * 是main线程终止了Thread-0
             * interrupt 只是设置了一个中断标志
             * 所以目标线程怎么处理这个中断标志 是他自己的事情
             * 一般来讲 目标线程 轮询中断标志 如果收到了中断标志 则抛出中断异常 依次达到结束自己的效果
             *
             *
             */
            thread.interrupt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(tName() + " end!");

    }

    //这仅仅只是设置了一个标志位
    private static void interruptBySelf(){
        Thread.currentThread().interrupt();
    }



    private static String tName() {
        return Thread.currentThread().getName();
    }
}
