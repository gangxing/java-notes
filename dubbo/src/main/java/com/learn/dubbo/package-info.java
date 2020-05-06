/**
 * @ClassName package-info
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 19/11/2018 14:40
 */
package com.learn.dubbo;

/**
 * Dubbo 原理
 * <p>
 * 先从官网看介绍 http://dubbo.incubator.apache.org/zh-cn/
 * <p>
 * Dubbo是一款高性能、轻量级的开源Java RPC框架。提供了三大核心能力： 1.面向接口的远程方法调用（这是RPC最基本的功能） 2.智能容错和负载均衡 智能容错怎么讲？？？ 所谓的智能容错
 * 是指重试？ Router 选择一个可调用的提供者子集 Load balance 根据负载均衡算法从可用的子集中选择一个特定的提供者实例 调用失败后重新选一个 细节：retries=2
 * 表示会最多重试2次 包括原始的一次 总共有3次
 * <p>
 * Dubbo 容错措施 FailOver 报错后自动切换实例重试 默认 FailFast 快速失败 只调用一次 失败抛异常 一般用于非幂等性的写操作（所以不方便重试？） FailSafe 失败安全
 * 失败时忽略 通常用于写入审计日志等操作（不属于主流程） FailBack 失败自动恢复 后台记录失败请求 定时重发 直至成功 一般用于消息通知 Forking 并行调用多个实例
 * 只要有一个成功即返回 通常用于实时性要求较高的读操作 但是会浪费更多服务资源 通过forks=2 设置最大并发数量 BroadCast 广播调用 任意一台报错即报错
 * 通常用于通知所有提供者更新缓存或日志等本地信息
 * <p>
 * <p>
 * 3.服务自动注册和发现 作为服务化管理最基本的功能
 * <p>
 * <p>
 * Dubbo 总体架构（这两天刚好在看在Dubbo 公众号就推送了这篇文章 他们是知道啥） https://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247486281&idx=1&sn=e744b1405b20a45a436f5dbf69a6dd5b&scene=21#wechat_redirect
 * 1.Dubbo 核心架构 Consumer Provider Container Registry Monitor 初始化、注册、订阅、调用和统计
 * <p>
 * Dubbo框架设计一共设置了10个层 1.服务接口层 Service 2.配置层 ServiceConfig ReferenceConfig 3.服务代理层 Proxy
 * 以ServiceProxy为核心，扩展接口为ProxyFactory (题外话：Spring实现AOP，IOC也主要是依靠代理实现，能看出代理对于框架实现是一个必须的手段) 静态代理
 * 动态代理（JDK动态代理 代理的对象必须是一个接口的实现；CGLB代理 基于字节码增强从而实现继承被代理的真实对象 所以不受被代理的真实对象必须要实现接口的限制）
 * <p>
 * 4.服务注册层 Registry 如果用直连模式 可以要注册中心 5.集群层 Cluster 将多个提供方伪装成一个提供方 并提供路由（这里的路由是什么意思）、负载均衡等功能，对上层透明
 * 6.监控层 Monitor 7.远程调用层 Protocol 封装RPC调用 Invoker 实体域 Dubbo的核心模型 代表一个可执行体 可向他发起invoke调用 Invocation
 * 从代码来看 是当前请求参数的封装体 属于会话域？ Protocol 服务域 是Invoker暴露和引用的主功能入口 负责Invoker的生命周期管理
 * <p>
 * 8.信息交换层 Exchange 封装请求响应模式 同步转异步 以Request和Response为中心 这里有个问题 Future的实现原理
 * 新线程怎么讲最终结果放到主线程的future对象中的 ？？？
 * <p>
 * 9.网络传输层 Transport 以Message为中心提供统一抽象接口（内部实现可替换 Netty or Mina）
 * <p>
 * 10.序列化层 Serialize Serialization ObjectInput(serialize) ObjectOut(deserialize) 可以通过SPI机制 实现这三个接口
 * 换用自定义的序列化实现
 * <p>
 * <p>
 * Dubbo详细流程图 https://mmbiz.qpic.cn/mmbiz/icNyEYk3VqGmp1nXKS9X6CDT9zX3oGFcoQKbZkB8KVVGntPdyb1OS5Japdeb6nYZr7PzhGTICZQNpyG8mneaCdQ/640?tp=webp&wxfrom=5&wx_lazy=1&wx_co=1
 * <p>
 * 自己看源码 绕来绕去 没有一个固化的流程 根本看不到什么 这是讲解dubbo的系列文章 先看看 https://blog.csdn.net/quhongwei_zhanqiu/article/list/3
 * <p>
 * 问题 0.Dubbo协议 所谓的dubbo协议和广义上和狭义上来讲分别是什么 采用NIO复用单一长连接，并使用线程池并发处理请求，减少握手和加大并发效率，性能较好（推荐使用）
 * 问题：一次完整的请求消费方和提供方会有几个线程参与 所谓的NIO复用单一长连接是什么意思 长连接和短连接（连接的本质） 正因为采用了单一长连接 所以dubbo对大数据量传输支持不是很好
 * <p>
 * dubbo://xxxxxx?xxx=xxx&yyy=yyy
 * <p>
 * 0.1 RPC （远程调用本质）
 * <p>
 * 1.（软）负载均衡算法实现 如果提供者数量稳定 基于任何算法都比较容易实现 但是提供者在不断地上线与下线 负载均衡算法怎么支持 默认为random 随机调用 可以自定义负载均衡实现
 * implements LoadBalance
 * <p>
 * 一致性Hash 虚拟节点 之前看到过 但是没搞懂其中的原理。。。。。。 这篇文章讲得还蛮清楚 https://blog.csdn.net/kefengwang/article/details/81628977
 * hash算法不保证均衡性 但其均衡性跟数据量成正比关系 一致性hash引入虚拟节点（物理节点的复制节点）来提高均衡性 要针对两种主体进行hash 1.节点在环上的位置映射 2.数据在环上的映射
 * 当数据的hash值等于节点的hash值时 刚好存在此节点上 否则顺时针找下一个节点
 * <p>
 * <p>
 * 采用虚拟节点的hash算法称为一致性hash 出现的背景 采用hash时 如果节点数量发生变化（即除数变化，基本上所有的数据的余数都变了 所以每个数据存放的节点都变了） 这样节点变化成本很高
 * 但实际上又不能避免节点数量的变化 比如因为负荷增加而增加节点、节点down掉而导致节点 数量减少
 * <p>
 * 采用虚拟节点后 当增减节点后 只影响到与变动节点相邻的一个或两个节点 其他部分与原来保持一致？？？？ 当增减节点后 依旧要保证两个原则 1.是符合hash的 2.是均匀的 通过下面这篇文章
 * https://my.oschina.net/lionets/blog/288066 确实满足了上面2个原则 但是从1、2、3节点
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 2.注册中心采用Master-Slave集群方式，可以实现自动选主，以实现注册中心的高可用行（HA） 选主算法 如果自己也有业务需要一个集群 怎么做到自动选主？
 * <p>
 * dubbo超时（包括所有的超时）是怎么实现的 新启一个线程来监控？ https://www.cnblogs.com/ASPNET2008/p/7292472.html
 * <p>
 * <p>
 * <p>
 * 1.超时配置详解 很多地方可以配置 重点关注优先级 方法级优先，接口级次之，全局配置再次之。如果级别一样，则消费方优先，提供方次之。
 * <p>
 * 官方建议在提供方配置超时 因为接口的大概耗时时间提供者更清楚 另外消费者无需再关心超时配置
 * <p>
 * 所以这里明确一个概念 不管dubbo的超时配置在提供者端还是消费者端，对超时的处理是同一套逻辑。 并不是说超时配在了消费者端 就叫消费者端超时 配在了提供者端 就叫提供者端超时
 * <p>
 * 超时的实现原理
 * <p>
 * 消费者端发起远程请求后，线程不会阻塞等待服务端的返回，而是马上的得到一个ResponseFuture，消费端通过不断轮询 机制判断是否有返回。
 * <p>
 * 基于这种机制 超时是必须的 否则会存在一直轮询的情况。
 * <p>
 * 所以dubbo默认的超时时间是 在各种Config类中没找到 直接看调用的地方 还是没找到 直接找轮询的地方 看看是否对timeout值有判断
 * <p>
 * <p>
 * RemotingInvocationTimeoutScan 从这点来看 确实新起了一个线程一直轮询Future
 * <p>
 * 所以 到底怎么实现超时判定的 不清楚。。。。只能调试了
 * <p>
 * 怎么还有一个TimerTask 每处理一个请求 就新建一个线程 并睡到预期的超时时间 如果这时候请求还没处理完 就 可以判定是超时了？？ 现在总共有三种了实现方式了 再慢慢研究
 * <p>
 * <p>
 * <p>
 * <p>
 * Dubbo 线程模型
 * <p>
 * 了解Dubbo的线程模型 先了解Netty的线程模型 http://www.cnblogs.com/ASPNET2008/p/7106820.html 单线程模式 所有请求都用一个线程完成
 * 由于Netty是用是NIO（非阻塞IO）采用单线程接收请求并处理所有的IO操作，然后将请求分发给业务线程处理 这就是所谓的多路复用？ 当请求量不大的时候 这种线程模型可以胜任 但是当请求量增大后
 * 一个线程处理所有的IO操作显得很吃力 （PS:所谓的IO操作具体干些什么？？）
 * <p>
 * 单接收多工作线程模式 接收请求还是一个线程，但是具体业务逻辑分发给工作线程池（workerGroup）处理
 * <p>
 * 多接收多工作线程模式 当请求量进一步增大后 所有请求接收靠单个线程完成也显得吃力 所以将请求接收线程也池化 叫接收线程池(bossGroup)
 * <p>
 * 以上就是Netty的线程模型
 * <p>
 * 对于上面的业务层，大多数应用都伴随着数据库操作，所以一般都是IO密集型操作，而非计算密集型操作 所以业务应该提供自己的处理线程池来处理业务逻辑 防止用netty的线程
 * 从而阻塞了Netty自身的线程
 * <p>
 * 接收请求线程主要负责创建链路（什么叫创建链路？？？），然后将请求分派给工作线程 工作线程主要负责编码解码 读取IO等操作，再将请求分派给业务线程（BusinessGroup??）
 * 业务线程主要负责具体的业务逻辑处理
 * <p>
 * 至于请求要不要从IO线程派发到业务线程池中 看Dubbo的Dispatcher策略配置 all 所有消息都派发到线程池 默认值 direct 所有消息都不派发到线程池 直接在IO线程里处理
 * message 只有请求响应消息派发到线程池 其他如连接断开、心跳等信息直接在IO线程上执行 execution 只有请求消息派发到线程池，不含响应。响应、其他连接断开事件、心跳等信息直接在IO线程上执行
 * connection 在IO线程里，将连接断开事件放入队列，有序逐个执行，其他消息派发到线程池。
 * <p>
 * <p>
 * Dubbo线程池
 * <p>
 * <p>
 * <p>
 * dubbo 多协议配置 因为dubbo采用单长连接模式 对大数据量传输支持不友好 可以考虑用多协议（将服务暴露在不同端口） 大数据用短连接协议（rmi） 小数据大并发用长连接协议（dubbo）
 * <p>
 * 服务分组 官方提供的使用场景:同一个接口有多种实现
 * <p>
 * 异步调用 使用场景：如果消费者需要同时调用两个不相关的接口 同时两个接口耗时比较长 这是可以异步调用两个接口 再通过RpcContext获取到Future 阻塞拿到真正的业务结果
 * 这样消费者最需要等待两个接口的耗时的最大值 否则如果同步调用两个接口的话 消费者需要等待两个接口耗时的总和
 * <p>
 * 本地调用 injvm协议 Dubbo会优先调用本地接口（如果消费者有当前接口实现的话，不是远程调用，但也会走Filter链） （TODO :Filter链怎么实现的 ？？？）
 * <p>
 * 参数回调 可以从服务端（提供者）调用客户端（消费者）逻辑
 * <p>
 * 优雅停机 是通过jdk的ShutdownHook实现的 配置等待时间 默认是10s 在kill -9 PID下不生效 只有在kill PID情况下才生效
 * <p>
 * 访问日志 accesslog=true
 * <p>
 * 服务容器 默认用spring容器 TODO
 * <p>
 * 分布式事务 还未实现
 * <p>
 * 线程栈自动dump 当线程池满时 自动dump到user.home目录下
 * <p>
 * 协议 dubbo rmi hessian(这不是一个序列化框架么？？) http webservice thrift memcached redis rest
 * <p>
 * <p>
 * <p>
 * 同类比较
 * <p>
 * <p>
 * 可以从Dubbo中借鉴的方面
 * <p>
 * <p>
 * <p>
 * Dubbo需要优化的方面
 */