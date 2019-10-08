package com.learn.druiddemo;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/5 12:02
 */
public class DruidDatasourceSample {
    public static void main(String[] args) {
        Properties props = System.getProperties();
        //数据库连接配置
        props.put("druid.name", "DruidDatasource");
        props.put("druid.url", "jdbc:mysql://47.110.254.134:3306/demo?useUnicode=true&characterEncoding=utf8");
        props.put("druid.username", "root");
        props.put("druid.password", "Zjcgdatabase2018k");

        //连接池配置 TODO 内部全用props.getProperty 所以value一定要是字符串
        //最大活跃数
        props.put("druid.maxActive", "10");
        //初始化连接数
        props.put("druid.initialSize", "0");
        //TODO 最大等待时间？？？ 这里配置的没用 不会用到
        props.put("druid.maxWait", "1000");

        //非必须属性
//        props.put("druid.testWhileIdle",true);
//        props.put("druid.testOnBorrow",true);
//        props.put("druid.validationQuery","select 'x'");

        DruidDataSource dataSource = null;
        try {
            dataSource = new DruidDataSource();
            //这个超时时间是获取connection的超时时间
            /*
             * TODO 此时pool最大链接数是10个，没问题，同时有10个是活跃的，也就是有可以用的连接 为什么不能再获取了。
             * TODO 难道是用了的是个连接没有返回到池子中去？
             *
             */
            dataSource.setMaxWait(3000);
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                new Thread(new InsertTask(dataSource)).start();
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                //用完的连接一定要返还到连接池，不然上面的getConnection将会无连接可取
                //FIXME 所谓的get 和recycle在连接数组上的操作是什么？
                /*
                 * 这里有个很重要的变量poolingCount 代表池子中剩余的链接个数(即数组中元素的个数),
                 * 取出连接. connection = connections[poolingCount-1]; connections[poolingCount-1] = null;
                 * 返还链接 connections[poolingCount] = connection
                 * FIXME 核心逻辑很清晰 但是取出和返还是并发条件下的 是怎么支持并发的
                 * FIXME 创建连接和销毁链接是在什么时刻下产生的
                 * 先看销毁
                 * 两个线程一会儿是Task 一会儿又是Thread 云里雾里的 再看看
                 * 其实就是两个thread。销毁线程启动后 每隔1秒定时扫描所有链接 如果已经有超时的，就驱逐之
                 * 再看创建连接线程
                 * 跟销毁线程类似，开启后设置为守护线程，但是它的运行不再是定时
                 * 而是通过生产者和消费者模型，利用两个条件notEmpty和empty来调控的
                 * 当获取链接时，如果已经空了，但是还没有达到最大，则等待，并通知创建线程开始创建1个链接
                 *
                 */
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
        }
    }

    private static class InsertTask implements Runnable {

        private DruidDataSource dataSource;

        public InsertTask(DruidDataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        public void run() {
            try {
                DruidPooledConnection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                String name = "王五" + System.currentTimeMillis() + "-" + Thread.currentThread().getName();
                String sql = "INSERT INTO student(`name`,`gender`,`birthday`,`height`,`created_at`) VALUES ('" + name + "',0,'2019-09-22',150,'2019-10-04 23:33:45')";

                int affectRows = statement.executeUpdate(sql);
                System.err.println("affectRows:" + affectRows);

                //用完的连接一定要返还到连接池，不然上面的getConnection将会无连接可取
                //FIXME 所谓的get 和recycle在连接数组上的操作是什么？
                /*
                 * 这里有个很重要的变量poolingCount 代表池子中剩余的链接个数(即数组中元素的个数),
                 * 取出连接. connection = connections[poolingCount-1]; connections[poolingCount-1] = null;
                 * 返还链接 connections[poolingCount] = connection
                 * FIXME 核心逻辑很清晰 但是取出和返还是并发条件下的 是怎么支持并发的
                 *
                 * FIXME
                 */
                connection.recycle();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
