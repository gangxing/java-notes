作为一个web开发者，主要面对的是数据库的操作，
即常说的CRUD。问到常用的技术栈，也能说个大概，比如采用MVC分层模式
框架用到了SpringMVC Spring X ,Mybatis,Druid,Mysql,Tomcat.先不管为什么要用这一套，或者跟同类相比
。这一套的选择的背景是什么。先从操作数据库开始一步一步到实现一张表的增删改查，再到最后提供一套完整的增删改查接口
逐一研究所用到的每一个框架的左右以及大概执行流程。
第一个模块，实现纯粹插入一条数据，见模块jdbc-demo

实现数据库的操作，需要通过SQL（Structure Query Language）操作。
其中SQL分为
DDL Data Definition Language 库表结构定义 对库或表结构的增删改查
DML Data Manipulation Language 操作数据 增删改查
DCL Data Control Language 访问权限和安全级别
其中使用最频繁的是DML，即定义好库表结构后，对数据的增删改查。
为了利用程序实现对数据的增删改查，即拼装SQL再执行，Sun为了简化、统一对数据库的操作，定义了一套Java操作数据库的规范，称之为JDBC
Java Database Connectivity
