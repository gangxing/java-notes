package com.learn.webdemo.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/26 10:57
 */
@Getter
@Setter
public class UserEntity extends BaseEntity {

  private String name;

  private String avatar;

  private Date birthday;

  private Integer fanCount;

  private Integer followCount;


}
