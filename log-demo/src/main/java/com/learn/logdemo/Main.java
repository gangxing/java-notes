package com.learn.logdemo;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/24 15:37
 */
public class Main {

  static {
    System.setProperty("logback.configurationFile", "console-pattern.xml");

  }


  public static void main(String[] args) {
    LoggerSample.main(args);
  }

}
