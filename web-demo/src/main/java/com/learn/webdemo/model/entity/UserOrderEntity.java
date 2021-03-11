package com.learn.webdemo.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: XingGang
 * @create: 2020-09-11 16:07
 */
@Getter
@Setter
public class UserOrderEntity extends BaseEntity {


  private String orderSn;
  private Long userId;



}
