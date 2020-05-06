package com.learn.jdk.jvm;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/21 13:42
 */
@Getter
@Setter
public class Student extends Person {

  private int classNo;

  private int level;


  public static void main(String[] args) {
    Student student = new Student();

  }


}
