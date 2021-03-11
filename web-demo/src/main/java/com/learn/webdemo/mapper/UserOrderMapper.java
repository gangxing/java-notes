package com.learn.webdemo.mapper;

import com.learn.webdemo.model.entity.UserOrderEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:11
 */
public interface UserOrderMapper {


  int insert(UserOrderEntity entity);

  Object listTables();
  List showTable(@Param("dbName")String db,@Param("tableName")String tableName);


}
