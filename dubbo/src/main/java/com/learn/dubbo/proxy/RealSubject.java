package com.learn.dubbo.proxy;

/**
 * @ClassName RealSubject
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 20/11/2018 17:20
 */
public class RealSubject implements Subject {

    @Override
    public void rent() {
        System.err.println("rent has been invoked");
    }

    @Override
    public String hello(String s) {
        String res= "hello " + s;
        System.err.println(res);
        return res;
    }
}
