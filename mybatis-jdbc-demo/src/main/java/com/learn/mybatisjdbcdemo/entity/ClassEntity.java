package com.learn.mybatisjdbcdemo.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/25 09:58
 */
@Getter
@Setter
public class ClassEntity {

  private Long id;

  private Integer grade;

  private Integer num;

  private Integer studentCount;

  private Date createdAt;
  ;

  private Date updatedAt;

}
