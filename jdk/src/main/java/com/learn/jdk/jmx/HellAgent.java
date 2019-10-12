package com.learn.jdk.jmx;

import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/12 15:48
 */

/**
 * 经常看到xxxMBean
 * 包括Spring加载Druid也是这个鬼 先研究一下到底是个什么
 * https://www.cnblogs.com/dongguacai/p/5900507.html
 * Java Management Extension
 * 基本上用于管理，应用将自己想要暴露出来的信息或操作通过MBean暴露出来，其他程序可以展示或执行
 * 一般用于监控程序 Jconsole 比如Tomcat
 *
 * 另外还有一个RMI
 * Remote Method Invocation
 * 远程方法调用，跟反射类似 但是RMI可以调用远程JVM上实例的方法。
 * 说道这个又跟RPC有点相似了 Remote Procedure Invocation 比如Dubbo调用
 *
 * https://blog.csdn.net/youyou1543724847/article/details/83313067
 *
 *
 */
@Slf4j
public class HellAgent {

    public static void main(String[] args) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Hello hello = new Hello();
        ObjectName objectName = new ObjectName("jmxBean:name=hello");
        server.registerMBean(hello, objectName);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
