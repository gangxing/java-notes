package com.learn.webdemo.test;

import com.learn.webdemo.model.entity.UserEntity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {


  public static void main(String[] args) {
    UserEntity user = new UserEntity();
    user.setName("xxx");
    System.err.println(user.toString());
    System.err.println(Long.MAX_VALUE/10000/10000/1000);

String content="xxkk\uD83C\uDE35(//●⁰౪⁰●)//";
    Pattern pattern = Pattern.compile("[^\\u0000-\\uFFFF]");
    Matcher matcher = pattern.matcher(content);
    boolean f= matcher.find();
    System.err.println(f);

  }
}
