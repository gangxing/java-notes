package com.learn.webdemo.test;

import com.learn.webdemo.mapper.UserOrderMapper;
import com.learn.webdemo.model.entity.UserOrderEntity;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: XingGang
 * @create: 2020-09-11 16:12
 */
public class UserOderMapperTest extends BaseTest {

  @Autowired
  private UserOrderMapper userOrderMapper;

  @Autowired
  private DataSource dataSource;

  @Test
  public void testxx() {

    for (int i = 0; i < 999999; i++) {
      UserOrderEntity entity = new UserOrderEntity();
      entity.setUserId(i + 1L);
      entity.setOrderSn(entity.getUserId().toString());
      userOrderMapper.insert(entity);
      System.err.println("insert " + entity.getUserId());
      try {
        Thread.sleep(RandomUtils.nextLong(1, 10));
      } catch (InterruptedException e) {
      }
    }
  }

  @Test
  public void testListTables() {
    Object object = userOrderMapper.listTables();
    System.err.println(object);
  }


  @Test
  public void testShowTable() throws Exception {
    Statement statement = dataSource.getConnection().createStatement();
    ResultSet resultSet = statement.executeQuery(
        "select * from information_schema.columns where table_schema = 'trade_db2' and table_name = 'user_order'");
    while (resultSet.next()) {

      String columnName = resultSet.getString("COLUMN_NAME");
      System.err.println("column "+columnName);
    }
//    List list=userOrderMapper.showTable("trade_db2","user_order");
  }


}
