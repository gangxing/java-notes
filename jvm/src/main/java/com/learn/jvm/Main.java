package com.learn.jvm;

/**
 * @ClassName Main
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 16/12/2018 23:13
 */
public class Main {

    public static void main(String[] args) throws Exception{

        MyClassLoader myClassLoader=new MyClassLoader();
        myClassLoader.loadClass("");
    }
}
