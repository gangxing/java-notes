/**
 * @ClassName package-info
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 04/11/2018 22:26
 */
package com.learn.concurrent.lock;

/**
 * AbstractQueuedSynchronizer (AQS)
 * 是为基于FIFO实现锁的基础框架 它自己本身不提供任何实现
 * @see java.util.concurrent.locks.ReentrantLock
 * @see java.util.concurrent.locks.ReentrantReadWriteLock
 *
 * 核心概念
 * 队列：所谓队列，里面的元素是什么，当然是等待获取这把锁的线程，队列采用何种数据结构实现的？是否有界
 * 先研究队列元素Node 对线程的包装
 * 成员变量有thread
 * waitStatus:
 * prev 前一个节点
 * next 后一个节点
 * 核心是waitStatus
 *
 * CANCELLED=1;
 * 因为超时或中断而取消
 *
 *
 * SIGNAL=-1;
 *
 *
 *
 * CONDITION=-2;
 * 当前节点在一个Condition队列中
 *
 * PROPAGATE=-3;
 * 无条件传播
 *
 * 0
 * 除以上状态以外的状态，其为默认值
 *
 * 锁对象 Sync定义了
 * Node head
 * Node tail
 * int state
 * 三个成员变量
 *
 * 先以ReentrantLock调试 看其大概流程
 * 在ReentrantLock中，state定义为当前锁被获取过的次数（因为是可重入锁）
 * 初始化锁，state = 0，没什么逻辑
 * 获取锁，尝试原子更新state 0->1 如果更新成功，说明获取锁成功，如果更新失败，则说明锁已经被其他线程获取了
 * 这时应该讲当前线程加入到队列中去？大概是要加到队列中去 妈的好复杂
 * 有一个核心概念 Node SHARE  EXCLUDE
 *
 * 对于lock的初步研究见<code>ReentrantLockSample</code>
 *
 *
 * 锁的思路
 *
 * 关键逻辑
 *
 * 怎么使用 定义子类实现它的时候需要注意什么
 *
 * 实现一个非重入锁,主要是在重写tryAcquire逻辑中的处理，如果当前线程已经获取了锁，则阻止再次获取，
 * 这里的实现有一点要注意，如果直接返回false的话，当前线程就会被无限期阻塞，因为这次获取不到被阻塞着，
 * 前面一次的获取再也没机会释放了。所以可以抛出异常或者中断。也可以重新实现tryLock，都是立即返回，不会抛出异常
 *
 * 一把锁可根据如下几个维度或组合来提供不同特性
 * 1.是否共享 共享锁；互斥锁
 * 2.是否可重入 可重入锁；不可重入锁 okay
 * 3.获取锁的顺序 公平锁；非公平锁
 *
 *
 * 互斥模式下，某个线程持有了锁，这个线程就是锁的持有者。
 * 但是在共享模式下，又该怎么实现？主要是在释放锁的时候，怎么确定当前线程是否获取过锁呢？？？
 * 锁的状态state又该怎么定义呢？？？？
 * 去ReentrantReadWriteLock一探究竟
 * 先大概预测下可重入读写锁的实现方案
 *
 *
 *
 *
 *
 *
 *
 *
 */
