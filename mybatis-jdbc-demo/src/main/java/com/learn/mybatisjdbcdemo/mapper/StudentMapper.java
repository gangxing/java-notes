package com.learn.mybatisjdbcdemo.mapper;

import com.learn.mybatisjdbcdemo.entity.StudentEntity;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/24 16:42
 */
public interface StudentMapper {

    StudentEntity selectById(Long id);

    int insert(StudentEntity studentEntity);

}
