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
出现Driver Connection Statement几个概念









