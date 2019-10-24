package com.learn.mybatisjdbcdemo.mapper.impl;

import com.learn.mybatisjdbcdemo.entity.StudentEntity;
import com.learn.mybatisjdbcdemo.mapper.StudentMapper;
import com.mysql.cj.protocol.Resultset;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/24 16:42
 */
public class StudentMapperImpl implements StudentMapper {

    private SqlSession session;

    public StudentMapperImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public StudentEntity selectById(Long id) {

        return session.selectOne("StudentMapper.selectById",id);
    }

    @Override
    public int insert(StudentEntity studentEntity) {
        return 0;
    }
}
