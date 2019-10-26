package com.learn.mybatisjdbcdemo.mapper;

import com.learn.mybatisjdbcdemo.entity.ClassEntity;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/24 16:42
 */
public interface ClassMapper {

    ClassEntity selectById(Long id);

    int insert(ClassEntity entity);

}
