package com.learn.jdk;

/**
 * @Description 自定义进制 2~62进制 支持持正整数
 * @Author xgangzai
 * @Date 2019/11/13 11:00
 */
public final class CustomDigit implements Comparable<CustomDigit> {

  private static final String BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  /**
   * 10进制数字
   */
  private int decimal;
  /**
   * 字符串形式
   */
  private String string;
  /**
   * 进制
   */
  private int digit;

  public CustomDigit(int decimal, int digit) {
    this.decimal = decimal;
    this.digit = digit;

    if (digit < 2 || digit > 62) {
      throw new IllegalArgumentException("digit must between 2 and 62");
    }

    if (decimal < 0) {
      throw new IllegalArgumentException("decimal must greatter than 0");
    }


  }

  public CustomDigit(String string, int digit) {
    this.string = string;
    this.digit = digit;


  }

  private void init() {

  }

  /**
   * 转换
   *
   * @param digit
   * @return
   */
  public CustomDigit trans(int digit) {

    return null;
  }


  @Override
  public int compareTo(CustomDigit o) {
    return this == o ? 0 : this.digit - o.digit;
  }

  @Override
  public String toString() {
    return string;
  }
}
