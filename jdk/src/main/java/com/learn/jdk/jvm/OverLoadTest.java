package com.learn.jdk.jvm;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/21 11:33
 */
public class OverLoadTest {

    public void method(int a){
        System.err.println("primite"+a);
    }

    public void method(Integer a){
        System.err.println("object"+a);
    }

    public static void main(String[] args) {
        OverLoadTest test=new OverLoadTest();
        test.method(Integer.valueOf(1));
    }
}
