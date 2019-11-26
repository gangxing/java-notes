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
public class BaseEntity implements Serializable {
    private Long id;

    private Date createdAt;

    private Date updatedAt;
}
