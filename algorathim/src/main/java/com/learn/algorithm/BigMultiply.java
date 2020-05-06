package com.learn.algorithm;

import java.util.Random;

/**
 * @ClassName BigMultiply
 * @Description 大数相乘
 * @Author xgangzai@gmail.com
 * @Date 2019/8/7 16:36
 */
public class BigMultiply {


  public static void main(String[] args) {
//        String s1 = s(1 << 17);
//        String s2 = s(1 << 17);
        /*for (int i=0;i<100;i++) {
            Random random = new Random();
            String s1 = random(1 << random.nextInt(6));
            System.err.println("s1= " + s1);
            String s2 = random(1 << random.nextInt(7));
            System.err.println("s2= " + s2);
            BigMultiply bm = new BigMultiply();
            String s = bm.multiply(s1, s2);
            System.err.println("result= " + s);

            //校验
            BigDecimal result = new BigDecimal(s1).multiply(new BigDecimal(s2));
            boolean right = new BigDecimal(s).compareTo(result) == 0;
            System.err.println(s1 + " * " + s2 + " = " + s + " " + right);
            System.err.println(right + " " + s);


        }*/

    check();
  }

  private static String random(int length) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append(random.nextInt(10));
    }
    return sb.toString();
  }

  private static String s(int len) {
    char[] c = new char[len];
    for (int i = 0; i < len; i++) {
      c[i] = '9';
    }
    return new String(c);
  }

  /*
   * 任意两个0-9的数字相乘，结果的十位+各位不超过10
   * 56 48 28 还是会超过10
   */
  private static void check() {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        int res = i * j;
        int sum = res / 10 + res % 10;
        System.err.println(i + " x " + j + "=" + res + "; sum=" + sum);
      }
    }
  }

  public String multiply(String s1, String s2) {
    char[] c1 = s1.toCharArray();
    int[] arr1 = new int[c1.length];
    for (int i = 0; i < c1.length; i++) {
      int b = c1[i] - '0';
      if (b < 0 || b > 9) {
        throw new IllegalArgumentException("有非数字字符" + c1[i]);
      }
      arr1[i] = b;
    }

    char[] c2 = s2.toCharArray();
    int[] arr2 = new int[c2.length];
    for (int i = 0; i < c2.length; i++) {
      int b = c2[i] - '0';
      if (b < 0 || b > 9) {
        throw new IllegalArgumentException("有非数字字符" + c2[i]);
      }
      arr2[i] = b;
    }

    int[] result = new int[arr1.length + arr2.length];
    for (int i = arr1.length - 1; i >= 0; i--) {
      for (int j = arr2.length - 1; j >= 0; j--) {
        result[i + j + 1] += (arr1[i] * arr2[j]);
      }
    }

    max(result);

    for (int i = result.length - 1; i > 0; i--) {
      result[i - 1] += result[i] / 10;
      result[i] = result[i] % 10;
    }

    StringBuilder sb = new StringBuilder();
    for (int i : result) {
      sb.append(i);
    }

    return sb.toString();
//
//        for (int i = 0; i < result.length; i++) {
//            result[i] += '0';
//        }
//
//        return new String(result);
  }

  private void max(int[] arr) {
    int max = 0;
    for (int i : arr) {
      if (i > max) {
        max = i;
      }
    }
    System.err.println("max=" + max);
  }
}
