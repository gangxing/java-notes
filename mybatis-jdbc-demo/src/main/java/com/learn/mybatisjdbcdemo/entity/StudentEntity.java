package com.learn.mybatisjdbcdemo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/24 16:40
 */
@Getter
@Setter
public class StudentEntity {

    private Long id;

    private String name;

    private Integer gender;

    private Date birthday;

    private Integer height;

    private Long classId;

    private Date createdAt;

    private Date updatedAt;

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", height=" + height +
                ", classId=" + classId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
