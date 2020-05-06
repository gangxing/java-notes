package com.leran.mybatisjdbcdemo.test;

import com.alibaba.fastjson.JSON;
import com.learn.mybatisjdbcdemo.entity.ClassEntity;
import com.learn.mybatisjdbcdemo.mapper.ClassMapper;
import com.learn.mybatisjdbcdemo.mapper.impl.ClassMapperImpl;
import com.learn.mybatisjdbcdemo.util.SqlSessionFactoryUtils;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/25 10:09
 */
@Slf4j
public class ClassMapperTest {

  @Test
  public void testInsert() {

    ClassEntity entity = new ClassEntity();
    entity.setGrade(1);
    entity.setNum(1);
    entity.setStudentCount(0);
    entity.setCreatedAt(new Date());

    SqlSessionFactory sessionFactory = SqlSessionFactoryUtils.getDefaultFactory();

    ClassMapper mapper = new ClassMapperImpl(sessionFactory.openSession());
    mapper.insert(entity);
    log.info(JSON.toJSONString(entity));
  }
}
