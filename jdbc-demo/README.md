实现数据库的操作，需要通过SQL（Structure Query Language）操作。
其中SQL分为
DDL Data Definition Language 库表结构定义 对库或表结构的增删改查
DML Data Manipulation Language 操作数据 增删改查
DCL Data Control Language 访问权限和安全级别
其中使用最频繁的是DML，即定义好库表结构后，对数据的增删改查。
为了利用程序实现对数据的增删改查，即拼装SQL再执行，Sun为了简化、统一对数据库的操作，定义了一套Java操作数据库的规范，称之为JDBC
Java Database Connectivity
为了实现Java操作数据，需要用到的组件
1.定义的JDBC见java.sql包
其中有如下几个概念
Connection

2.JDBC只是一个规范，并未提供实现，针对不同的数据库，其厂商各自提供了驱动包，即JDBC的实现，
对MYSQL来讲，JDBC的实现是mysql-connector-java.当然，msyql对其他语言的实现可能叫mysql-connector-python xxxx

先从使用角度来讲，实现了插入数据，见方法insert,期间遇到一个编码问题，没设置编码为utf-8,中文插入到数据库是问号
[JDBC详解](https://blog.csdn.net/shuaicihai/article/details/53416045)
出现DriverManager Connection Statement几个概念
DriverManager
JDK提供的不同数据库的驱动管理类，核心功能初始化具体驱动后，提供Connection对象
实现逻辑
xxx
用到了SPI 

Connection
JDK提供的连接数据库的对象，每一次SQL执行，是在一个Connection环境下

Statement
JDK提供，封装SQL并执行
ps 
PreparedStatement 是Statement的子接口，可以提供动态SQL，动态的只能是字段值，不能是表名、字段名或操作符等会影响SQL逻辑的字符

FIXME：
Mybatis中是怎么利用$符号实现动态的

ResultSet
JDK提供的用于封装查询结果对象

通过DriverManager获取Connection,通过Connection获取Statement,Statement执行SQL语句

FIXME
怎么体现事务
[JDBC事务使用](https://www.iteye.com/blog/yangzg216-1186085)
默认是自动提交的，更新SQL执行后，立即提交，如果想切换到手动提交模式，则
connection.setAutoCommit(false).
手动提交是为了可以控制回滚，如果某一条SQL执行失败后，则回滚特定的SQL，
具体回滚哪些SQL，是根据设置的安全点来界定的，
Savepoint savepoint=connection.setSavepoint()
connection.rollback(savepoint)
如果未设置安全点，则当前connection下所以已经执行的SQL都会回滚
测试之

















