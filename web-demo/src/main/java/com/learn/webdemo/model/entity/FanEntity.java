package com.learn.webdemo.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 10:59
 */
@Getter
@Setter
public class FanEntity extends BaseEntity {


  private Long userId;

  private Long fanUserId;

  private Integer delFlag;
}
