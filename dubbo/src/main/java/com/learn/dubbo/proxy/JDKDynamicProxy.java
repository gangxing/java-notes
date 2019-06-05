package com.learn.dubbo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName DynamicProxy
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 20/11/2018 17:21
 */
public class JDKDynamicProxy implements InvocationHandler {

    //真实对象
    private Object subject;

    //当前类就是subject的代理对象
    public JDKDynamicProxy(Object subject) {
        this.subject = subject;
    }


    /**
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        System.err.println("before execute " + methodName);

//        Object result = method.invoke(subject, args);
        Object result = method.invoke(subject, args);

//        if (1==1){
//            throw new RuntimeException("throw on purpose");
//        }

        System.err.println("after execute " + methodName);
        return result;
    }
}
