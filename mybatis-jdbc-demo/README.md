https://blog.csdn.net/qq_32166627/article/details/70741729


https://blog.csdn.net/hellozpc/article/details/80878563

原来mybatis也实现了一套连接池
PooledDataSource implements
TODO 确定是自己完整的实现还是利用dbcp啊？

问题
一、怎么实现java code配置SQLsessionfactory

主要分为两部分
1.连接池构造，能够获取connection
2.解析mapper文件，将每一条SQL解析成一个statement
有了connection，有了statement就可以执行SQL了。

二、怎么换用druid连接池
在初始化SqlSessionFactory时，要提供Environment,其中需要一个DataSource。这时可以提供自己想要的
DataSource实现给mybatis。

mybatis具体的架构和源码分析后续再看
初步看，mybatis特爱用建造者模式和工厂模式



**事务管理**
`Transaction`,通过`TransactionFactory`创建。有三套
jdbc
什么都不做，没有事务提交或回滚的概念？

managed
什么都不做，交由外部容器处理

spring


其实从SqlSessionFactory，SqlSession到TransactionFactory,Transaction都有三套


问题
1.这三套的比较


2.一个事务不提交和回滚有和异同
最终效果类似。都不会持久化到数据库。不同点在于
回滚，立即撤销当前更改。
不提交，等到当前连接被销毁时(默认空闲8小时后，MySQL服务器销毁链接)，撤销更新
因此，在连接存活的情况下，当前更新（虽然未提交）对READ_UNCOMMIT是可见的。
综上，在实际使用过程中，一个更新完成后，根据场景要么提交，要么回滚掉。不要留在那里。

所谓事务，在MySQL层面，start transaction  end .
对于select语句，默认是开启的还是未开启事务的，两者有何区别







