package com.learn.datastructure.util;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName ArrayRandomUtils
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/3/20 22:00
 */
public class ArrayRandom {

  public static int[] random(int length, int min, int max) {
    Random random = new Random();
    int[] arr = new int[length];
    for (int i = 0; i < length; i++) {
      arr[i] = min + random.nextInt(max - min);
    }
    return arr;
  }

  public static void main(String[] args) {
    System.err.println(Arrays.toString(random(10000000, 100, 1000)));
  }
}
