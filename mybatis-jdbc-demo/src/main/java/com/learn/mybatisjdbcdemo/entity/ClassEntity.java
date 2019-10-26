package com.learn.mybatisjdbcdemo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

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

    private Date createdAt;;

    private Date updatedAt;

}
