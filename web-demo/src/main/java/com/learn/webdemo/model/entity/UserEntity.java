package com.learn.webdemo.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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
