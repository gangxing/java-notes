package com.learn.webdemo.mapper;

import com.learn.webdemo.model.entity.UserEntity;
import com.learn.webdemo.model.query.UserQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:11
 */
public interface UserMapper {

    UserEntity selectById(Long id);

    int insert(UserEntity entity);

//    int insertBatch(Map<String, Object> map);

    UserEntity selectOne(@Param("query") UserQuery query);

    List<UserEntity> selectList(@Param("query") UserQuery query, @Param("sort") Sort sort);

    List<UserEntity> pageList(@Param("query") UserQuery query, @Param("sort") Sort sort, @Param("page") Pageable pageable);

    Integer count(@Param("query") UserQuery query);

    int updateById(@Param("update") UserEntity update);

    int updateByIds(@Param("update") UserEntity update, @Param("ids") List<Long> ids);


    int incrCount(@Param("id") Long id,
                  @Param("fanCount") Integer fanCount,
                  @Param("followCount") Integer followCount);

}
