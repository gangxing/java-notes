package com.learn.jdbcdemo;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/4 22:58
 */
public class Main {
    public static void main1111(String[] args) throws ClassNotFoundException , SQLException {
        /*
         *
         * Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'.
         * The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
         *
         */
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://47.110.254.134:3306/demo","root","Zjcgdatabase2018k");
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT *  FROM student");
        while (rs.next()) {
            String name = rs.getString("A_NAME");
            System.out.println("name is:"+ name);
        }
        rs.close();
        stmt.close();
        connection.close();
    }

    public static void main(String[] args)throws ClassNotFoundException,SQLException {
        insert();
    }


    private static void insert()throws ClassNotFoundException,SQLException{
//        Class.forName("com.mysql.cj.jdbc.Driver");
        //这里一定要设置编码参数，否则默认是latin1? 中文插入到数据库是两个问号
        PrintStream out=System.err;
        DriverManager.setLogWriter(new PrintWriter(out));
        String url="jdbc:mysql://47.110.254.134:3306/demo?useUnicode=true&characterEncoding=utf8";
        Connection connection = DriverManager.getConnection(url,"root","Zjcgdatabase2018k");
        Statement stmt = connection.createStatement();
        //为什么中文是乱码
        String sql="INSERT INTO student(`name`,`gender`,`birthday`,`height`,`created_at`) VALUES ('王五',0,'2019-09-22',150,'2019-10-04 23:33:45')";
        int affectRows = stmt.executeUpdate(sql);
        System.err.println("affectRows:"+affectRows);
        stmt.close();
        connection.close();
    }
}
