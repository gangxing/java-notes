package com.learn.algorithm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName RandomStringUtils
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/3/19 23:40
 */
public class RandomUtils {

  private static char[] LOWWER_CASE = new char[26];

  static {
    for (int i = 0; i < 26; i++) {
      LOWWER_CASE[i] = (char) ('a' + i);
    }
  }

  /**
   * random a string with specified length
   *
   * @param length
   * @return
   */
  public static String randomString(int length) {

    if (length == 0) {
      return "";
    }

    char[] chars = new char[length];
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      chars[i] = LOWWER_CASE[random.nextInt(LOWWER_CASE.length)];
    }
    return new String(chars);
  }

  public static int[] randomArray(int length, int min, int max) {
    int[] arr = new int[length];
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      arr[i] = random.nextInt(max - min) + min;
    }
    return arr;
  }

  public static int[] randomArrayUnique(int length, int min, int max) {
    if (max - min < length) {
      throw new IllegalArgumentException("不可能产生这么多了");
    }
    int[] arr = new int[length];
    Object object = new Object();
    Map<Integer, Object> map = new HashMap<>(length);
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      while (true) {
        int value = random.nextInt(max - min) + min;
        Object old = map.put(value, object);
        if (old == null) {
          arr[i] = value;
          break;
        }
      }
    }
    return arr;
  }


  public static void main(String[] args) {
    char c = 'a';
//        System.err.println((char) (c + 1));

    int len = 100;
    for (int i = 0; i < 100; i++) {

      System.err.println(i + "-->" + (char) i);
    }
    System.err.println(1);

  }
}
