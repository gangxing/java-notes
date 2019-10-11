/**
 * @ClassName package-info
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 22/11/2018 15:06
 */
package com.learn.concurrent.thread;


/**
 * 线程的5个状态 状态切换的方式（线程的方法、Object方法）
 * https://www.cnblogs.com/hongdada/p/6482966.html
 * NEW
 * RUNNABLE(可运行的，至于有没有真正在运行，要看当前时刻有没有获得CPU时间片)
 * BLOCKED
 * WAITING
 * TIMED_WAITING
 * TERMINATED
 * <p>
 * <p>
 * <p>
 * <p>
 * interrupt 详解
 * <p>
 * 发现好多地方用到了interrupt方法 一脸懵逼 找了好多篇文章才找用比较靠谱的说明
 * <link>https://juejin.im/post/5ba062be6fb9a05cd84918b7</link>
 * interrupt 是干什么用的 出现的背景 怎么使用 需要注意的点
 * <p>
 * 停止线程是多线程
 * <p>
 * /**
 * <p>
 * interrupt 详解
 * <p>
 * 发现好多地方用到了interrupt方法 一脸懵逼 找了好多篇文章才找用比较靠谱的说明
 * <link>https://juejin.im/post/5ba062be6fb9a05cd84918b7</link>
 * interrupt 是干什么用的 出现的背景 怎么使用 需要注意的点
 * <p>
 * 停止线程是多线程
 * <p>
 * interrupt 详解
 * <p>
 * 发现好多地方用到了interrupt方法 一脸懵逼 找了好多篇文章才找用比较靠谱的说明
 * <link>https://juejin.im/post/5ba062be6fb9a05cd84918b7</link>
 * interrupt 是干什么用的 出现的背景 怎么使用 需要注意的点
 * <p>
 * 停止线程是java多线程开发中很重要的技术点，在很多业务场景中需要用到（TODO 找一个具体的场景）
 */

/**
 * interrupt 详解
 *
 * 发现好多地方用到了interrupt方法 一脸懵逼 找了好多篇文章才找用比较靠谱的说明
 * <link>https://juejin.im/post/5ba062be6fb9a05cd84918b7</link>
 * interrupt 是干什么用的 出现的背景 怎么使用 需要注意的点
 *
 * 停止线程是java多线程开发中很重要的技术点，在很多业务场景中需要用到（TODO 找一个具体的场景）
 * 在老版本JDK中，停止一个线程使用的方式是Thread.stop(),但是后面发现这种处理方式是很危险且不安全的，所以
 * 现在已被标记为@Deprecated(TODO 至于stop为什么 参见官方说明<link>https://docs.oracle.com/javase/6/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html</link> 虽然看不懂)
 * 取而代之的可以用interrupt来叨叨停止线程的目的
 *
 * interrupt 原意是打断、中断
 * 但是在JDK中 这个方法并不会直接终止一个正在运行的线程
 * 使用 得到要停止的线程实例 调用其targetThread.interrupt()
 *
 * TODO 线程状态的流转
 *
 */
