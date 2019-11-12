package com.learn.concurrent.threadcooperation;

/**
 * @Description 多个线程ABCD，按顺序各自输出ABCD
 * @Author xgangzai
 * @Date 2019/11/12 11:49
 */
public class PrintInOrderJoin {

    public static void main(String[] args) throws Exception{
        Thread A=new Thread(new Printer("A"),"A");
        Thread B=new Thread(new Printer("B"),"B");
        Thread C=new Thread(new Printer("C"),"C");
        Thread D=new Thread(new Printer("D"),"D");
        A.start();
        //join 语义：阻塞直至A线程结束
        A.join();

        B.start();
        B.join();

        C.start();
        C.join();

        D.start();
        D.join();
    }


    private static class Printer implements Runnable {

        private String s;

        public Printer(String s) {
            this.s = s;
        }

        @Override
        public void run() {

            System.err.println(tName() + " " + s);

        }

        private String tName() {
            return Thread.currentThread().getName();
        }
    }
}
