package com.learn.mybatisjdbcdemo.mapper.impl;

import com.learn.mybatisjdbcdemo.entity.ClassEntity;
import com.learn.mybatisjdbcdemo.entity.StudentEntity;
import com.learn.mybatisjdbcdemo.mapper.ClassMapper;
import com.learn.mybatisjdbcdemo.mapper.StudentMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/24 16:42
 */
public class ClassMapperImpl implements ClassMapper {

    private SqlSession session;

    public ClassMapperImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public ClassEntity selectById(Long id) {

        return session.selectOne("ClassMapper.selectById", id);
    }

    @Override
    public int insert(ClassEntity entity) {
        return session.insert("ClassMapper.insert", entity);
    }
}
