package com.learn.datastructure.util;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/8/14 16:46
 */
public class Bytes {

  private static char[] doToBinaryString(byte b) {
    char[] chars = new char[8];
    for (int i = chars.length - 1; i >= 0; i--) {
      int v = b >>> chars.length - 1 - i & 1;
      chars[i] = (char) ('0' + v);
    }
    return chars;
  }

  public static String toBinaryString(byte[] bytes) {
    int len = bytes.length;
    char[] chars = new char[len * 9];
    int ci = 0;
    for (int i = 0; i <= bytes.length - 1; i++) {
      byte b = bytes[i];
      char[] oneChars = doToBinaryString(b);
      for (char c : oneChars) {
        chars[ci++] = c;
      }
      chars[ci++] = ' ';

    }
    return new String(chars);
  }

  public static String toBinaryString(byte b) {
    return new String(doToBinaryString(b));
  }

}
