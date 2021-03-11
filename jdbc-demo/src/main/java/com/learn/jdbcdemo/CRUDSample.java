package com.learn.jdbcdemo;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/4 22:58
 */
public class CRUDSample {

  public static void main(String[] args) {
        insert();
//        update();
//    query();
  }


  private static void insert() {
    //这里一定要设置编码参数，否则默认是latin1? 中文插入到数据库是两个问号
    Connection connection = null;
    Statement statement = null;
    try {

      PrintStream out = System.err;
      DriverManager.setLogWriter(new PrintWriter(out));
      String url = "jdbc:mysql://127.0.0.1:3306/sancheng?useUnicode=true&characterEncoding=utf8";
      connection = DriverManager.getConnection(url, "root", "1234");
      connection.setCatalog("learn_db");
      statement = connection.createStatement();
      //为什么中文是乱码
      String sql = "INSERT INTO `user`(`name`,`created_at`) VALUES ('王五',now())";
      int affectRows = statement.executeUpdate(sql);
      System.err.println("affectRows:" + affectRows);
    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ee) {
          ee.printStackTrace();
        }
      }

      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException eee) {
          eee.printStackTrace();
        }
      }
    }
  }

  private static void update() {
    Connection connection = null;
    Statement statement = null;
    try {
      PrintStream out = System.err;
      DriverManager.setLogWriter(new PrintWriter(out));
      String url = "jdbc:mysql://47.110.254.134:3306/demo?useUnicode=true&characterEncoding=utf8";
      connection = DriverManager.getConnection(url, "root", "Zjcgdatabase2018k");
      statement = connection.createStatement();
      //为什么中文是乱码
      String sql = "UPDATE  student SET name='王巴丹' where id=1000007";
      int affectRows = statement.executeUpdate(sql);
      System.err.println("affectRows:" + affectRows);
    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ee) {
          ee.printStackTrace();
        }
      }

      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException eee) {
          eee.printStackTrace();
        }
      }
    }
  }

  private static void query() {
    Connection connection = null;
    Statement statement = null;
    try {
      PrintStream out = System.err;
      DriverManager.setLogWriter(new PrintWriter(out));
      String url = "jdbc:mysql://47.110.254.134:3306/demo?useUnicode=true&characterEncoding=utf8";
      connection = DriverManager.getConnection(url, "root", "Zjcgdatabase2018k");
      statement = connection.createStatement();
      //为什么中文是乱码
      String sql = "select * from   student ";
      ResultSet resultSet = statement.executeQuery(sql);
      while (resultSet.next()) {
        Integer id = resultSet.getInt("id");
        System.err.println("id=" + id);
        String name = resultSet.getString("name");
        System.err.println("name=" + name);
        Integer gender = resultSet.getInt("gender");
        System.err.println("gender=" + gender);

        Date birthday = resultSet.getDate("birthday");
        System.err.println("birthday=" + birthday);

        Integer height = resultSet.getInt("height");
        System.err.println("height=" + height);

        Date createdAt = resultSet.getDate("created_at");
        System.err.println("createdAt=" + createdAt);

      }
    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ee) {
          ee.printStackTrace();
        }
      }

      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException eee) {
          eee.printStackTrace();
        }
      }
    }
  }
}
