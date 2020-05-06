package com.learn.jdbcdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/12 21:15
 */
@Slf4j
public class TransactionLevelSample {

  private static final String URL = "jdbc:mysql://47.110.254.134:3306/demo?useUnicode=true&characterEncoding=utf8";
  private static final String USER_NAME = "root";
  private static final String PASSWORD = "Zjcgdatabase2018k";
  /**
   * 事务隔离级别学习（最终目标，搞清楚SpringTX mybatis druid driver每个环节的工作）
   * <p>
   * 两个事务并发修改同一条数据，试验在不同隔离级别下。结果的区别 两个事务跟是否一定需要两个线程？？？
   * <p>
   * 所谓的事务本质是什么？反应在MySQL代码层面，是加锁了还是干什么了？
   * <p>
   * JDBC每执行一条SQL都是在事务中吗？如果是，上层那些个事务传播是怎么控制的？ 针对第一个问题，先尝试从driver中能否得到答案
   * <p>
   * <p>
   * Connection级的事务隔离级别和MySQL服务器上设置的默认隔离级别什么关系 我的理解事务级别是Connection级的。创建一个Connection本质上就是跟MySQL服务器建立一个socket连接。
   * 在此过程中，先从MySQL服务器上拿到默认的隔离级别设置给Connection,如果后续不再设置隔离级别，那么此次Connection 的隔离级别就是MySQL服务器上设置的默认值
   * <p>
   * 上层Spring TX中开启事务和不开启事务本质上在控制着什么？？？？
   */

  volatile static int flag = 0;

  public static void main(String[] args) {
    try {
      dobiz();
//            levelTest();
    } catch (Exception e) {
      e.printStackTrace();
//            log.error("exception occur:"+e.getMessage(),e);
    }
  }


  private static void levelTest() throws Exception {
    Connection connA = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    //默认是4(TRANSACTION_REPEATABLE_READ)
    int level = connA.getTransactionIsolation();
    log.info("default level {}", level);
    Statement statement = connA.createStatement();
    int timeoutSeconds = statement.getQueryTimeout();//0
    log.info("default timeout in {} seconds", timeoutSeconds);
  }

  public static void dobiz() throws Exception {
    //SERIALIZABLE情况下，事务A会加锁（什么锁），等待超时（默认多长时间）后尝试重启事务（有没有重启，报错了链接是否断开了？）
    //既然在等待，说明事务B已经先前一步持有锁了？在更新数据的事务中，锁又是什么样的存在 什么时刻获取，什么时刻释放，具体是什么
    //类型的锁？
    int isolationLevelA = Connection.TRANSACTION_SERIALIZABLE;
    int isolationLevelB = Connection.TRANSACTION_REPEATABLE_READ;

    Connection connA = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    connA.setAutoCommit(false);
//        connA.setReadOnly(true);
    connA.setTransactionIsolation(isolationLevelA);
    Connection connB = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    connB.setAutoCommit(false);
    //事务A读到的数据是什么情况，只跟事务A的隔离级别有关系，是不受事务B隔离级别的影响的
//        connB.setTransactionIsolation(isolationLevel);

    Statement statA = connA.createStatement();
    Statement statB = connB.createStatement();

    //A查询得到1
    Integer a1 = select(statA);
    log.info("a1=" + a1);

    //FIXME 禁止重排？
    flag = 1;

    //B查询得到1
    Integer b1 = select(statB);
    log.info("b1=" + b1);

    flag = 2;

    //B 1->2
    update(statB);

    flag = 3;
    Integer v1 = select(statA);
    log.info("v1=" + v1);

    flag = 4;

    //B 提交事务 TODO 在什么时候开启事务呢？？？
    connB.commit();

    flag = 5;

    Integer v2 = select(statA);
    log.info("v2=" + v2);

    connA.commit();

    Integer v3 = select(statA);
    log.info("v3=" + v3);


  }

  private static void update(Statement statement) throws SQLException {
    String sql = "UPDATE  student SET gender=2 where id = 1017452";
    int affectRows = statement.executeUpdate(sql);
    log.info("affectRows=" + affectRows);
  }

  private static Integer select(Statement statement) throws SQLException {
    String sql = "SELECT gender FROM  student where id = 1017452";
    ResultSet resultSet = statement.executeQuery(sql);
    if (resultSet.next()) {
      return resultSet.getInt("gender");
    }
    return null;

  }
}
