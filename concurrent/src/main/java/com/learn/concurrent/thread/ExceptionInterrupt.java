package com.learn.concurrent.thread;

/**
 * @ClassName ExceptionInterrupt
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 22/11/2018 15:17
 */
public class ExceptionInterrupt extends Thread {


    @Override
    public void run() {
        super.run();
        String tName=Thread.currentThread().getName();
        for (int i = 0; i < 500000; i++) {
            /*
             * isInterrupted 不会修改数据 只是返回当前线程是否被中断了
             * interrupted 会清除中断标志 换一种说法再次调用这个方法时 会返回false
             */
            if (this.isInterrupted()) {
                System.err.println(tName+"已经是停止状态了！我要退出了！");
                break;
            }
            System.err.println(tName+"i=" + (i + 1));
        }
        System.err.println(tName+"我被输出，如果此代码是for又继续运行，线程并未停止！");

    }
}
