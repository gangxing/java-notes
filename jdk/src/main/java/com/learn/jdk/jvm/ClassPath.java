package com.learn.jdk.jvm;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/20 14:17
 */
public class ClassPath {

  public static void main(String[] args) {
    System.out.println("Bootstrap ClassLoader path: ");
    System.out.println(System.getProperty("sun.boot.class.path"));
    System.out.println("----------------------------");

    System.out.println("Extension ClassLoader path: ");
    System.out.println(System.getProperty("java.ext.dirs"));
    System.out.println("----------------------------");

    System.out.println("App ClassLoader path: ");
    System.out.println(System.getProperty("java.class.path"));
    System.out.println("----------------------------");
  }
}
