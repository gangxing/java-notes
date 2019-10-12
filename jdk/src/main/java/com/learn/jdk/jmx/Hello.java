package com.learn.jdk.jmx;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/12 15:46
 */
@Slf4j
public class Hello implements HelloMBean {

    private String name;

    private Integer age;

    @Override
    public String getName() {
        log.info("getName invoked name=" + name);
        return name;
    }

    @Override
    public void setName(String name) {
        log.info("setName invoked name=" + name);
        this.name = name;
    }

    @Override
    public Integer getAge() {
        log.info("getAge invoked age=" + age);
        return age;
    }

    @Override
    public void setAge(Integer age) {
        log.info("setAge invoked age=" + age);
        this.age = age;
    }

    @Override
    public void eat(String param) {
        log.info("eat invoked param:"+param);
    }

    @Override
    public String toString() {
        return "Hello{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
