package com.leran.mybatisjdbcdemo.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.learn.mybatisjdbcdemo.entity.StudentEntity;
import com.learn.mybatisjdbcdemo.mapper.StudentMapper;
import com.learn.mybatisjdbcdemo.mapper.impl.StudentMapperImpl;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/24 16:44
 */

@Slf4j
public class StudentMapperTest {

    private SqlSessionFactory sessionFactory;
    @Before
    public void before() throws IOException {
//        String resource="mybatis.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        sessionFactory=new SqlSessionFactoryBuilder().build(inputStream);

        //根据上面研究Configuration的构建流程
//        PooledDataSource dataSource=new PooledDataSource();
//        dataSource.setDriver("com.mysql.cj.jdbc.Driver");
//         dataSource.setUrl("jdbc:mysql://47.110.254.134:3307/demo?useUnicode=true");
//         dataSource.setUsername("root");
//         dataSource.setPassword("Zjcgdatabase2018k");

        DataSource dataSource=initDataSource();
        TransactionFactory transactionFactory=new JdbcTransactionFactory();
        String id="development";
        Environment environment=new Environment(id,transactionFactory,dataSource);
        Configuration configuration = new Configuration(environment);

//        configuration.addMapper(StudentMapper.class);
//        String mapperResource="StudentMapper.xml";
//        InputStream mapperInputStream = Resources.getResourceAsStream(mapperResource);
//        XMLMapperBuilder mapperParser = new XMLMapperBuilder(mapperInputStream, configuration, mapperResource, configuration.getSqlFragments());
//        mapperParser.parse();
        List<Mapper> mapperList=new ArrayList<>();
        mapperList.add(new Mapper(StudentMapper.class,"StudentMapper.xml"));
        parseMappers(configuration,mapperList);

        sessionFactory = new DefaultSqlSessionFactory(configuration);


    }

    //这就是接口是实现分开的好处，可以用自己想用的实现方案
    private DataSource initDataSource(){
        Properties props = System.getProperties();
        //数据库连接配置
        props.put("druid.name", "DruidDatasource");
        props.put("druid.url", "jdbc:mysql://47.110.254.134:3307/demo?useUnicode=true&characterEncoding=utf8");
        props.put("druid.username", "root");
        props.put("druid.password", "Zjcgdatabase2018k");

        //连接池配置  内部全用props.getProperty 所以value一定要是字符串
        //最大活跃数
        props.put("druid.maxActive", "10");
        //初始化连接数
        props.put("druid.initialSize", "0");
        return  new DruidDataSource();
    }

    private void parseMappers(Configuration configuration, List<Mapper> mapperList)throws IOException{
        for (Mapper mapper:mapperList){
            configuration.addMapper(mapper.mapperClass);
            String mapperResource=mapper.mapperXml;
            InputStream mapperInputStream = Resources.getResourceAsStream(mapperResource);
            XMLMapperBuilder mapperParser = new XMLMapperBuilder(mapperInputStream, configuration, mapperResource, configuration.getSqlFragments());
            mapperParser.parse();
        }
    }

    private static class Mapper{
        private Class mapperClass;
        private String mapperXml;

        public Mapper(Class mapperClass, String mapperXml) {
            this.mapperClass = mapperClass;
            this.mapperXml = mapperXml;
        }
    }

    @Test
    public void testQuery(){

        SqlSession session=sessionFactory.openSession();
        StudentMapper mapper=new StudentMapperImpl(session);
        StudentEntity entity=mapper.selectById(1000682L);
        log.info("Student Entity:"+ JSON.toJSONString(entity));


    }
}
