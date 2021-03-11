package com.learn.hbase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @description:
 * @author: XingGang
 * @create: 2020-10-05 14:25
 */
public class HBaseCRUD {

  private static final String rowKey = "id";

  private static final String FAMILY = "f";

  //内部字段
  private static final String VERSION = "_version";

  private static Admin admin;

  static Configuration conf;

  static Connection connection;

  static {

    conf = HBaseConfiguration.create();

    conf.set("hbase.zookeeper.quorum", "127.0.0.1:2181");
    conf.set("hbase.master", "localhost:16010");

    try {
      connection = ConnectionFactory.createConnection(conf);
      admin = connection.getAdmin();
    } catch (MasterNotRunningException e) {
      e.printStackTrace();
    } catch (ZooKeeperConnectionException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  public static void createTable(String tableName) throws Exception {

//    dropTable(tableName);
    if (tableExists(tableName)) {
      System.err.println("table " + tableName + " exists");
      return;
    }
    TableName tableNameObj = TableName.valueOf(tableName);

    HTableDescriptor hTableDescriptor = new HTableDescriptor(tableNameObj);

    //至少要一个family
    hTableDescriptor.addFamily(new HColumnDescriptor(FAMILY));

    admin.createTable(hTableDescriptor);

    System.out.println("create table successed");

  }

  public static void dropTable(String tableName) throws IOException {
    TableName tableNameObj = TableName.valueOf(tableName);
    if (!tableExists(tableName)) {
      return;
    }
    //删除之前要将表disable
    if (!admin.isTableDisabled(tableNameObj)) {
      admin.disableTable(tableNameObj);
    }
    admin.deleteTable(tableNameObj);
  }

  public static boolean tableExists(String tableName) throws IOException {
    return admin.tableExists(TableName.valueOf(tableName));
  }


  /**
   * 一次只能插入一个字段？？
   *
   * @param tableName
   * @param rowKey
   * @param family
   * @param column
   * @param value
   * @throws IOException
   */

  static Table table = null;

  public static void put(String tableName, String rowKey, String family, Map<String, String> data)
    throws IOException {
    prepareTable(tableName);

    byte[] familyBytes = Bytes.toBytes(family);
    Put put = new Put(Bytes.toBytes(rowKey));
    for (Entry<String, String> entry : data.entrySet()) {
      put.addColumn(familyBytes, Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
    }
    table.put(put);
    //increase version
    long postVersion = table.incrementColumnValue(Bytes.toBytes(rowKey), familyBytes, Bytes.toBytes(VERSION), 1);
  }

  /**
   * @param tableName
   * @param rowKey
   * @return
   * @throws IOException
   */
  public static Map<String, String> get(String tableName, String rowKey)
    throws IOException {
    prepareTable(tableName);

    Get get = new Get(Bytes.toBytes(rowKey));
    Result result = table.get(get);

    Map<String, String> data = new HashMap<>();
    Cell[] cells = result.rawCells();
    if (cells != null && cells.length > 0) {
      for (Cell cell : cells) {
        String column = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
        if (VERSION.equals(column)) {
          long version = Bytes.toLong(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
          data.put(column, String.valueOf(version));
        } else {
          data.put(column, Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
        }
      }
    }

    return data;

  }


  private static void prepareTable(String tableName) throws IOException {
    if (table == null) {
      table = connection.getTable(TableName.valueOf(tableName));
    }
  }

  public static void main(String[] args) throws Exception {

    String tableName = "teacher";

//    createTable(tableName);
//    dropTable(tableName);

//    put(tableName);

    get(tableName);



  }

  private static void put(String tableName) throws IOException {
    long start = System.currentTimeMillis();
    Map<String, String> data = new HashMap<>(4);
    for (int i = 1; i < 10000; i++) {
      //id
      data.put("id", String.valueOf(i));

      //name
      data.put("name", "teach中文er_" + i);
      //age
      data.put("age", String.valueOf(RandomUtils.nextInt(100)));
      //gender
      data.put("gender", String.valueOf(RandomUtils.nextBoolean()));

      put(tableName, String.valueOf(i), FAMILY, data);
    }
    long time = System.currentTimeMillis() - start;
    System.err.println(time + " ms");
  }


  private static void get(String tableName) throws IOException {
    for (int i = 1; i < 10000; i++) {
      Map<String, String> data = get(tableName, String.valueOf(i));
      System.err.println(data);
    }
  }


}
