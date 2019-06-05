package com.learn.dubbo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @ClassName Main
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 20/11/2018 17:24
 */
public class Main {

    static {
        /* 设置此系统属性,让JVM生成的Proxy类写入文件 */
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
    }
    public static void main(String[] args) {

        Subject realSubject = new RealSubject();

        InvocationHandler handler = new JDKDynamicProxy(realSubject);

        /*
         * Proxy 是所有动态代理类的父类
         * subject 实例是$Proxy0 继承了Proxy 实现了 Subject
         * 所以subject拥有真实对象realSubject的实例方法，但是subject同时持有handler实例
         * 所以subject的实例方法实际是委托给handler来处理的 而handler持有realSubject实例
         * 所以handler的处理方式是加入自己的逻辑 中间调用realSuject对应的实例方法（原始的真正的逻辑）
         * 当然handler是自己实现的 具体要处理什么逻辑 自己实现
         */
        Subject subject = (Subject) Proxy.newProxyInstance(handler.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), handler);

        subject.rent();
        subject.hello("二狗子");

        System.err.println(subject.getClass().getName());


    }
}
