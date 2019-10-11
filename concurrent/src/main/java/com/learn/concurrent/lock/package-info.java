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
 * 一把锁可根据如下几个维度或组合来提供不同特性
 * 1.是否可重入 可重入锁；不可重入锁 okay
 *  实现一个非重入锁,主要是在重写tryAcquire逻辑中的处理，如果当前线程已经获取了锁，则阻止再次获取，
 *  这里的实现有一点要注意，如果直接返回false的话，当前线程就会被无限期阻塞，因为这次获取不到被阻塞着，
 *  前面一次的获取再也没机会释放了。所以可以抛出异常或者中断。也可以重新实现tryLock，都是立即返回，不会抛出异常
 *
 * 2.是否共享 共享锁；互斥锁
 * AQS原生提供了两套获取锁和释放锁API，
 * 一套是互斥的：tryAcquire  tryRelease
 * 一套是共享的：tryAcquireShared tryReleaseShared
 * 所以在具体实现锁的时候，根据需求，选择其中一套来实现
 * 对于共享锁，我猜测就跟可重入锁类似，每获取一次，将state加1。每释放一次，将state减1。
 * 当state等于0时，表示这把锁没有被任何线程持有。这样有点违背常规意义上锁的语义，那有何意义呢。
 * 其实并没有，常规意义上的锁，是为了保证线程绝对的串行执行，也就是同一时刻只允许有一个线程持有这把锁。
 * 但有没有同一时刻允许多于1个小于N个线程执行的场景呢？我的理解是有的，比如场景的servlet请求，为了控制
 * 服务的负荷，需要限制请求的并发量。同一时刻最多只处理N个请求。这就是信号量Semaphore刚好就提供这个功能
 * 内部应该就是采用共享模式的锁，看了下源码，其实现基本就是上面猜测的逻辑。
 * 同类，CountDownLatch CyclicBarrier应该也是类似的实现。
 * CountDownLatch
 * await语义实现：尝试获取锁，如果state（代表还剩余的计数）大于0，将主线程加入队列阻塞
 * countDown语义实现：将state减1，当减为0时，唤醒队列中所有被阻塞的线程
 *
 * CyclicBarrier
 * state同样做计数 await()语义实现类似于获取锁和释放锁二合一操作，
 * 每调一次await(),state减1，如果state大于0，说明还有线程没到，则将当前线程加入队列挂起。
 * 当最后一个线程达到屏障点。将state减1 变成0，则唤醒线程中所有队列
 *
 * 上面研究了这么多 Node的两种模式又是什么鬼？？？？
 *
 *
 *
 *
 *
 *
 * 3.获取锁的顺序 公平锁；非公平锁
 * <link>https://juejin.im/post/5ae755736fb9a07acd4d829c</link>
 * 所谓的公平与不公平不在于队列中被唤醒的次序。一直以为不公平是从队列里随机取一个线程唤醒。
 * 实际上体现在
 * 公平锁，在尝试获取锁的时候，会先判断队列中是否存在等待的线程，如果有，则放弃争抢锁，AQS会将其放入队列中
 * 非公平锁，在尝试获取锁的时候，不管队列中是否有等待的线程，先直接尝试获取(有可能队列中还有等待的线程，
 * 而自己这时刚好获取到了，这就是不公平的体现)，如果获取不到，AQS会将其加入队列
 * 所以两个的根本区别在于获取锁的时候是否要判断队列是否有等待的线程。如果被加入到队列后，都是按照FIFO顺序被唤醒。
 * 公平与否特性是由子类自己定义实现的，AQS原生中没有相关概念
 *
 *
 *
 * waitStatus状态流转
 *
 *
 * 队列进出
 *
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
