package com.learn.jdk;

/**
 * @ClassName PrimitiveType
 * @Description Java基础数据类型
 * @Author xgangzai@gmail.com
 * @Date 2019/6/10 23:45
 */
public class PrimitiveType {

  public static void main(String[] args) {
    long l = 10000;
    System.err.println(Long.toBinaryString(l));
    long ll = Long.reverseBytes(l);
    String s = Long.toBinaryString(ll);
    System.err.println(s);
    System.err.println(s.length());
  }
}
