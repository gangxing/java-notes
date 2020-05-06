package com.learn.mybatisjdbcdemo.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.learn.mybatisjdbcdemo.mapper.ClassMapper;
import com.learn.mybatisjdbcdemo.mapper.StudentMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/25 10:05
 */
@Slf4j
public class SqlSessionFactoryUtils {

  private static final SqlSessionFactory DEFAULT_FACTORY;
  private static final SqlSessionFactory SQLSESSION_MANAGER;

  private static final Configuration CONFIGURATION;

  static {
    DataSource dataSource = initDataSource();
    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    String id = "development";
    Environment environment = new Environment(id, transactionFactory, dataSource);
    Configuration configuration = new Configuration(environment);

    List<Mapper> mapperList = new ArrayList<>();
    mapperList.add(new Mapper(StudentMapper.class, "StudentMapper.xml"));
    mapperList.add(new Mapper(ClassMapper.class, "ClassMapper.xml"));
    parseMappers(configuration, mapperList);

    CONFIGURATION = configuration;

    DEFAULT_FACTORY = new DefaultSqlSessionFactory(configuration);
    SQLSESSION_MANAGER = SqlSessionManager.newInstance(DEFAULT_FACTORY);


  }

  //这就是接口是实现分开的好处，可以用自己想用的实现方案
  private static DataSource initDataSource() {
    Properties props = System.getProperties();
    //数据库连接配置
    props.put("druid.name", "DruidDatasource");
    props.put("druid.url",
        "jdbc:mysql://47.110.254.134:3307/demo?useUnicode=true&characterEncoding=utf8");
    props.put("druid.username", "root");
    props.put("druid.password", "Zjcgdatabase2018k");

    //连接池配置  内部全用props.getProperty 所以value一定要是字符串
    //最大活跃数
    props.put("druid.maxActive", "10");
    //初始化连接数
    props.put("druid.initialSize", "0");
    return new DruidDataSource();
  }

  private static void parseMappers(Configuration configuration, List<Mapper> mapperList) {
    try {
      for (Mapper mapper : mapperList) {
        configuration.addMapper(mapper.mapperClass);
        String mapperResource = mapper.mapperXml;
        InputStream mapperInputStream = Resources.getResourceAsStream(mapperResource);
        XMLMapperBuilder mapperParser = new XMLMapperBuilder(mapperInputStream, configuration,
            mapperResource, configuration.getSqlFragments());
        mapperParser.parse();
      }
    } catch (IOException e) {
      log.error("paser mappers exception{}", e.getMessage(), e);
    }
  }

  public static SqlSessionFactory getDefaultFactory() {
    return DEFAULT_FACTORY;
  }

  public static SqlSessionFactory getSqlsessionManager() {
    return SQLSESSION_MANAGER;
  }

  private static class Mapper {

    private Class mapperClass;
    private String mapperXml;

    public Mapper(Class mapperClass, String mapperXml) {
      this.mapperClass = mapperClass;
      this.mapperXml = mapperXml;
    }
  }
}
