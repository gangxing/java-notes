package com.learn.webdemo.mapper;

import com.learn.webdemo.model.entity.FanEntity;
import com.learn.webdemo.model.query.FanQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:11
 */
public interface FanMapper {

  FanEntity selectById(Long id);

  int insert(FanEntity entity);

//    int insertBatch(Map<String, Object> map);

  FanEntity selectOne(@Param("query") FanQuery query);

  List<FanEntity> selectList(@Param("query") FanQuery query, @Param("sort") Sort sort);

  List<FanEntity> pageList(@Param("query") FanQuery query, @Param("sort") Sort sort,
      @Param("page") Pageable pageable);

  Integer count(@Param("query") FanQuery query);

  int updateById(@Param("update") FanEntity update);


}
