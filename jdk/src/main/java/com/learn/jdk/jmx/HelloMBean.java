package com.learn.jdk.jmx;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/12 15:46
 */
public interface HelloMBean {

  String getName();

  void setName(String name);

  Integer getAge();

  void setAge(Integer age);

  public void eat(String param);


}
