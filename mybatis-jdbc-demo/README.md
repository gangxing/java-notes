https://blog.csdn.net/qq_32166627/article/details/70741729


https://blog.csdn.net/hellozpc/article/details/80878563

原来mybatis也实现了一套连接池
PooledDataSource implements
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

mybatis

spring



