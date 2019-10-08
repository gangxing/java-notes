Connection是一个较重的对象，频繁创建和销毁会影响效率
P.S.为什么说Connection是一个较重的对象，所谓的重是什么意思？我的理解是创建其过程复杂，耗时长
需要跟数据库建立socket链接，所以Connection是一个重对象

为了解决每次执行SQL都新建Connection这一重对象。JDBC在扩展包(javax.sql)中又提供了DataSource接口
P.S. PooledConnection是什么鬼

druid 对Datasource的实现则是DruidDatasource
druid 主要实现功能
1.主要实现了通过DruidDatasource对Connection
2.基于动态代理和调用链实现的链式调用？？？？各种过滤器，实现监控

使用详解
只要是创建DruidDatasource对象，并设置属性（配置）
后面从其获取Connection等操作都是原生的JDBC操作
配置主要分为以下几块
一、数据库连接配置
druid.url //连接url
druid.username //用户名
druid.password //密码

二、连接池配置
druid.maxActive //最大有效连接数 如果连接数达到这个值连接池怎么处理？
druid.initialSize //初始连接数量

这块配置还是比较复杂的，
只配置了上面两个参数
druid.maxActive=10
druid.initialSize=4
循环获取connection，并执行insert SQL，结果插入了八条数据，系统就一直卡死了
这又是一个好大的话题，只能慢慢来排查了
![WechatIMG888.png](http://ww1.sinaimg.cn/large/0079pfhqly1g7nf5rn2uxj31de0ben0f.jpg)
发现主线程从队列中取链接被阻塞了？？？
这里有两个线程 一个是创建线程 一个是销毁线程 有点类似生产者与消费者模式 
为什么会阻塞呢？？？？？ 先搞清楚逻辑再说
这个源代码好难梳理清楚啊 。。。。我的妈

找了半天，感觉[这篇](https://www.jianshu.com/p/16a646a4eccd)还比较靠谱


问题：
1.管理Connection的核心逻辑，以及其线程模型



2.责任链模式





