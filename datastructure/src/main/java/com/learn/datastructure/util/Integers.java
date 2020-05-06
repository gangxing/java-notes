package com.learn.datastructure.util;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/8/14 16:44
 */
public class Integers {

  public static byte[] bytes(int n) {
    byte[] bytes = new byte[4];
    for (int i = bytes.length - 1; i >= 0; i--) {
      bytes[i] = (byte) (n & (1 << 8) - 1);
      n >>>= 8;
    }

    return bytes;

  }


  public static int parseInt(byte[] bytes) {
    if (bytes == null || bytes.length != 4) {
      throw new IllegalArgumentException("the length of bytes must be 4");
    }
    int n = 0;
    int base = (1 << 8) - 1;
    for (int i = 0; i < 4; i++) {
      int one = base & bytes[i];
      one <<= 8 * (3 - i);
      n |= one;
    }

    return n;
  }

  public static void main(String[] args) {
    int i = 11;
    System.err.println(i);
    byte[] bytes = bytes(i);
    System.err.println(Bytes.toBinaryString(bytes));

    int ii = parseInt(bytes);
    System.err.println(ii);


  }
}
