package com.learn.webdemo.model.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 11:29
 */
@Getter
@Setter
public class FanQuery extends BaseQuery {

  private Long userId;

  private Long fanUserId;

  private Integer delFlag;
}
