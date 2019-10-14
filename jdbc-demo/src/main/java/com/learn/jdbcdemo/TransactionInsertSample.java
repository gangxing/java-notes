package com.learn.jdbcdemo;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Random;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/4 22:58
 */
public class TransactionInsertSample {

    public static void main(String[] args) {
        String sql1 = "INSERT INTO student(`name`,`gender`,`birthday`,`height`,`created_at`) VALUES ('王五1',0,'2019-09-22',150,'2019-10-04 23:33:45')";
        String sql2 = "INSERT INTO student(`name`,`gender`,`birthday`,`height`,`created_at`) VALUES ('王五2',0,'2019-09-22',150,'2019-10-04 23:33:45')";
        PrintStream out = System.err;
        DriverManager.setLogWriter(new PrintWriter(out));
        String url = "jdbc:mysql://47.110.254.134:3306/demo?useUnicode=true&characterEncoding=utf8";
        Connection connection = null;
        Statement statement = null;
        try {

            connection = DriverManager.getConnection(url,"root", "Zjcgdatabase2018k");
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            insert(sql1, statement);
//            insert(sql2, statement);
            //执行完之后，统一提交 如果不提交 不会落入磁盘
//            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }


    private static void insert(String sql, Statement statement) throws SQLException {
        int affectRows = statement.executeUpdate(sql);
        System.err.println("affectRows:" + affectRows);
        Random random = new Random();
        if (random.nextBoolean()) {
//            throw new RuntimeException("insert exception");
        }
    }


}
