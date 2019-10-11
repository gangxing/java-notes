/**
 * @ClassName package-info
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 02/11/2018 13:53
 */
package com.learn.concurrent.threadpool;


/**
 * 线程池采用的生产者消费者模式，FIXME 为什么不用常见的对象池模式，borrow return
 * 因为利用一个线程执行一个任务，只能通过构造器将任务指定给Thread.然后start。但是拿到一个创建好
 * 的Thread是没办法再指定Runnable给它的。这里其实Thread的概念与对象的概念有点误导。在java语言中，
 * 一个对象具有某种属性（成员变量）和某种功能（成员方法）。所以要想执行某个功能，只能先找一个具有该功能
 * 的对象，然后执行。但是对象在执行功能时是需要一个线程环境的。并且这个线程环境一定要在线程创建时就要指定任务。
 * 解释半天好像也没说通。。。。
 *
 * 一、线程池关键属性 coreSize idleSize e.g.
 * ThreadPoolExecutor
 * 1.corePoolSize  the number of threads to keep in the pool, even if they are idle,
 * unless {@code allowCoreThreadTimeOut} is set
 * 如果设置了核心线程超时 否则空闲的线程也会保留在线程池中
 *
 * 2.maximumPoolSize 线程池允许的最大线程数量
 *
 * 3.keepAliveTime 如果线程数量达到corePoolSize 超过corePoolSize数量的空闲线程存活的最大时间
 *
 * 4.workQueue 存放待执行的任务 调用execute方法后存放在workQueue
 *
 * 5.threadFactory 线程创建工厂 作用是指定县城priority, name, daemon status, {@code ThreadGroup}等参数
 *
 * 6.handler 拒绝执行处理器 当线程数量和workQueue容量都达到上限时 会拒绝执行任务 并且调用拒绝处理器
 *
 *
 * 四、ThreadPoolExecutor运行流程、任务执行流程、线程生命周期
 *
 * 线程池接收到任务是怎么处理的，线程怎么管理的（怎么销毁线程，又怎么保证线程存活的）
 *
 * 先看看Work 继承了AQS 实现了Runnable
 * 核心成员有 thread  firstTask
 * 当一个worker（线程）创建好，先将其加入到线程集合中，然后这个线程执行传入的任务（如果有）和一直从
 * 任务队列中取任务执行。直到任务队列空了，就被阻塞？？
 * 基本上是这样的，但是如果线程池状态是非运行中或者线程数量有什么异常，才会直接返回null 然后worker 结束
 * 一个worker退出还有可能会执行任务时抛出了异常，在剔除完这个死掉的线程后，判断根据线程数量判断是否需要创建新的线程
 * 来补充
 *
 * 研究驱逐空闲线程
 * 很巧妙
 * 按照常规理解，给每个线程搞一个最后执行任务时间，新启一个线程定时扫描 如果扫描到某个线程空闲到最大时间了，就将其
 * 结束
 * 实际上线程所谓的空闲，是在获取任务时阻塞了，根据配置，如果需要撤销多余的线程，那就在获取任务时定一个超时时间
 * 如果超时了，这个线程就结束啦，然后被剔除
 *
 *
 * 线程池接收任务的入口execute(runnable)
 *
 * 线程池从接收task到执行完task 流程分析
 * https://blog.csdn.net/u011240877/article/details/73440993
 *
 *
 * 线程池处理新任务的策略
 * 1.当线程数量小于核心线程数量时 新建线程执行任务
 * 2.当线程数量已达到核心线程数量后，任务队列未满，尝试将任务放入队列（这时可能有空闲的线程？那也不管）
 * 3.当核心池已满，队列也已满，尝试创建新线程（当前新增的任务由新建的线程执行？？？）
 * 4.如果新建线程失败（说明线程池已经关闭或者完全满了，达到最大线程数量） 则拒绝任务
 *
 *
 *
 *
 * 线程池状态 runState
 * RUNNING 接收新任务并处理队列中的任务 线程池新建时即为RUNNING状态
 *
 * SHUTDOWN 不再接收新任务 但是处理队列中的任务
 *
 * STOP 不再接收新任务 也不再处理队列中的任务 并且会中断处理中的任务
 *
 * TIDYING (原意为整理打包的意思) 所有任务已经完成 工作线程（workerCount）减为0 在转为TIDYING状态时会调用
 * terminated() 钩子
 *
 * TERMINATED terminated()方法执行完之后 线程池状态即为TERMINATED状态
 *
 * 线程池5个状态间的转换
 *
 * RUNNING --> SHUTDOWN 调用shutdown()
 *
 * RUNNING (or SHUTDOWN) --> STOP 调用shutdownNow()
 *
 * SHUTDOWN --> TIDYING 当队列和线程池都为空的时候
 *
 * STOP --> TIDYING 当线程池为空的时候
 *
 * runState 实现详解
 * 以高三位表示5个状态 分别是-1 0 1 2 3 左移29位
 * 因为两位最多只能表示4个状态 所以必须要用3位才能表示5个状态
 *
 * 剩下的29位表示线程容量
 *
 * CAPACITY (1<<29) -1 后29位 1 表示线程容量 大概500百万（5亿）
 * 这样便将runState和workerCount 用一个整数表示了
 * 如果后续CAPACITY不满足要求 就换用Long 只要够用就用Integer 因为这样运算更快一点 并且实现更简单一点
 * 所以 当知道整数值的时候 怎么反解出来runState和workerCount 这是重点
 * wokerCountOf(int c){
 *     return c & CAPACITY
 * }
 * 因为 CAPACITY的高三位是0 、 然后剩余的29位 实际值是多少就是多少
 *     相当于用CAPACITY 过滤掉高三位 （把runState值过滤掉）真他么巧妙
 *
 * 反解runState
 * runStateOf(int c){ return c & ~ CAPACITY; }
 * ~CAPACITY 是0  相当于过滤掉了CAPACITY 是实际的runState值
 *
 * 存放task用的BlockingQueue
 * 学习之
 * BlockingQueue 就是一个队列 提供插入 删除 查询三个核心功能
 * 但每一种功能有四种形式 在非正常的情况下：
 * 第一种 抛异常
 * 第二种 返回特殊值 null或者false
 * 第三种 一直阻塞当前线程（生产者消费者场景）
 * 第四种 限时等待 如果超时 则放弃 并返回特殊值
 *
 * Insert(按照上面四种顺序)
 * add offer put offer(e,time,unit)
 *
 *
 * Remove
 * remove poll take poll
 *
 * Examine
 * element peek 无实现 无实现
 *
 * 在这里先只了解这么多 BlockingQueue的实现原理 在集合框架里详细研究
 *
 * 几点额外的知识点
 * 1.BlockingQueue是线程安全的 因为内部用了锁或其他并发控制
 * 2.BlockingQueue不允许插入null值 否则会抛空指针
 * 3.如果不指定队列的容量 默认是Integer.MAX_VALUE
 *
 *
 *
 * 二、常用的线程池及其适用场景
 * ForkJoinPool
 *
 *
 *
 *
 * 三、任务排队策略
 * 三种主要策略
 * 1.直接处理（Direct hand off）
 * 用同步队列 如果没有可用的线程来处理 不能成功入列
 * 这种队列可以避免处理一系列有内部依赖的任务时相互锁住的情况
 * 这种策略需要一个无界的最大线程数量（不设置最大线程数量）
 *
 * 2.无界队列（Unbounded Queue）
 * 如果corePoolSize个线程在处理 则任务会加入队列 不会再新建线程 所以这种场景下
 * maximumPoolSize不再有意义
 * 这种策略适合没有相互依赖的任务
 *
 * 3.有界队列（Bounded Queue）
 * 这种策略最主要的目的是防止无限制的线程耗尽系统资源
 * 但这是一种不好控制的策略
 * 队列大小和maxPoolSize 相互trade off(矛盾？ 出卖？)
 * a.如果用大队列、小池子会最小化CPU使用率和系统资源 并且加重上下文切换负载 降低系统的吞吐量
 * 因为任务很多 但是线程数量就这么点 需要频繁的切换线程的上下文
 *
 * b.
 *
 * 三-一:任务拒绝策略
 * 在以下场景 任务会被拒绝（抛出RejectedExecutionException）然后交由RejectHandler来处理
 * 1.线程池关闭
 * 2.如果用的是有界队列和固定大小池子 并且已饱和
 * 提供了四种内置的拒绝策略
 * 1.AbortPolicy 默认的 直接抛出RejectedExecutionException异常
 * 2.DiscardPolicy 直接忽略
 * 3.DiscardOldestPolicy 将未处理的最老的任务清理掉
 * 4.
 *
 *
 * 如果线程池不再被引用 并且 已无线程了 线程池会自动关闭
 *
 *
 *
 *
 *
 *
 *
 *
 * 额外学习
 * 位运算 及其应用场景
 *
 * 至此，线程池管理任务，线程生命周期实现逻辑大体明确了，但因为并发环境下，加了很多锁和很多其他逻辑。
 * 暂时不过过深的研究
 *
 * 待研究完其他几种线程池后，全面的梳理下不同线程池模型适用的场景
 *
 *
 *
 *
 *
 *
 *
 *
 */
