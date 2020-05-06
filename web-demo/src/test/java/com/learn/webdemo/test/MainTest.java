package com.learn.webdemo.test;

import com.learn.webdemo.model.entity.UserEntity;

public class MainTest {


  public static void main(String[] args) {
    UserEntity user = new UserEntity();
    user.setName("xxx");
    System.err.println(user.toString());
  }
}
