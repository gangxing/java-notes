package com.leran.mybatisjdbcdemo.test;

import com.alibaba.fastjson.JSON;
import com.learn.mybatisjdbcdemo.entity.StudentEntity;
import com.learn.mybatisjdbcdemo.mapper.StudentMapper;
import com.learn.mybatisjdbcdemo.mapper.impl.StudentMapperImpl;
import com.learn.mybatisjdbcdemo.util.SqlSessionFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/24 16:44
 */

@Slf4j
public class StudentMapperTest {

    @Test
    public void testInsert() {
        StudentEntity entity = build();

        SqlSession session = SqlSessionFactoryUtils.getDefaultFactory().openSession();
        StudentMapper mapper = new StudentMapperImpl(session);
        log.info("insert {}",entity);
        int rows=mapper.insert(entity);

        //默认不会自动提交,所以需要手动提交,或者在获取session时指定自动提交
        session.commit();
        log.info("rows={}, entity={}",rows,entity);

    }

    @Test
    public void testInsert1() {
        StudentEntity entity = build();
        SqlSession session=SqlSessionFactoryUtils.getSqlsessionManager().openSession(true);
        StudentMapper mapper=new StudentMapperImpl(session);
        log.info("insert {}",entity);
        int rows=mapper.insert(entity);
        log.info("rows={}, entity={}",rows,entity);

    }

    private StudentEntity build() {

        StudentEntity entity = new StudentEntity();
        long now = System.currentTimeMillis();
        long birthday = RandomUtils.nextLong(now - 5 * YEAR, now) - 10 * YEAR;
        entity.setBirthday(new Date(birthday));
        entity.setClassId(RandomUtils.nextLong(1, 5));
        entity.setGender(RandomUtils.nextInt(0, 2));
        entity.setHeight(RandomUtils.nextInt(100, 160));
        entity.setCreatedAt(new Date());
        RandomStringGenerator generator=new RandomStringGenerator.Builder().build();
        String s=generator.generate(5);
        entity.setName(s);
        return entity;
    }

    private static final long YEAR = 365 * 24 * 60 * 60 * 1000L;

    @Test
    public void testQuery() {
        SqlSession session = SqlSessionFactoryUtils.getDefaultFactory().openSession();
        StudentMapper mapper = new StudentMapperImpl(session);
        StudentEntity entity = mapper.selectById(1000682L);
        log.info("Student Entity:" + JSON.toJSONString(entity));
    }


}
