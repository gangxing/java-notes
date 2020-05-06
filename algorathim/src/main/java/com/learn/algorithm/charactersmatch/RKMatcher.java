package com.learn.algorithm.charactersmatch;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/23 11:17
 */

/**
 * 对BF算法的优化 Rabin-Karp 核心思想是对source中n-m+1个子串和target分别计算其hash值， 然后比对n-m+1个hash中有没有跟target的hash相等的
 * <p>
 * 问题： 1.时间复杂度是多少，计算n-m+2次hash，比对n-m+1次。所以整体时间复杂度O(N)所以关键就在于hash函数的选择
 * 2.怎么解决hash冲突，还是在于hash函数的选择,当hash值一样时，再次逐个字符比对，感觉这是hash冲突的通用套路 解决办法：利用进制转换的思想，假如面对的字符集是52个英文字母和10个数字，那就将一个字符串当做一个62进制的数字，转换成
 * 10进制数字。当字符串过长时，会不会出现数字溢出的问题，先实现 计算的时候，有两点可以优化 1.计算第i个子串的时候，可以复用部分i-1个子串的计算结果。类似等比数列 2.可以将62^n
 * 次方的值缓存下来，用户的时候直接取就行了 n作为下标
 */
public class RKMatcher implements Matcher {

  //    private static final char[] BASE="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private static final String BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  static int[] rediuxCache;

  static {
    rediuxCache = new int[62];
    rediuxCache[0] = 1;
    for (int i = 1; i < 62; i++) {
      rediuxCache[i] = rediuxCache[i - 1] * 62;
    }
  }

  public static void main(String[] args) {
    RKMatcher matcher = new RKMatcher();

    String s = "1bk8383ddk";
//        int i = matcher.trans62toDecimal(s);
//        System.err.println(i);
//        String ss = matcher.decimalTo62(i);
//        System.err.println(ss + " ->" + ss.equals(s));

    int i = 62;
    int count = 1;
    int sum = 62;
    while (sum > 0 && sum < Integer.MAX_VALUE) {
      count++;
      sum = sum * i;
    }

    System.err.println(count);
  }

  /**
   * @param source
   * @param target
   * @return
   */
  @Override
  public int matches(String source, String target) {
    char[] sourceChars = source.toCharArray();
    char[] targetChars = target.toCharArray();

    int sourceLen = sourceChars.length;
    int targetLen = targetChars.length;
    if (sourceLen < targetLen) {
      return -1;
    }

    int targetHash = trans62toDecimal(targetChars, 0, targetLen);
    for (int i = 0; i <= sourceLen - targetLen; i++) {
      if (trans62toDecimal(sourceChars, i, targetLen) == targetHash) {
        return i;
      }
    }

    return -1;
  }

  private int trans62toDecimal(String s) {
    return trans62toDecimal(s.toCharArray(), 0, s.length());
  }

  /**
   * TODO 两个优化点 1.62^n 可以缓存下来，提高效率 2.from+1子串计算是否可以某种程度上利用from子串的计算结果
   *
   * @param chars
   * @param from
   * @param length
   * @return
   */
  private int trans62toDecimal(char[] chars, int from, int length) {
    int res = 0;
    for (int i = 0; i < length; i++) {
      char c = chars[from + i];
      int ii = BASE.indexOf(c);
      for (int j = 0; j < length - i - 1; j++) {
        ii *= 62;
      }
      res += ii;
    }

    return res;
  }

  //这种方案只支持正数
  private String decimalTo62(int d) {
    char[] chars = new char[10];
    int dd = d;
    int index = -1;
    while (dd > 0) {
      int m = dd % 62;
      chars[++index] = BASE.charAt(m);

      dd /= 62;
    }

    char[] res = new char[index + 1];
    int resIndex = 0;
    for (int i = index; i >= 0; i--) {
      res[resIndex++] = chars[i];
    }

    return new String(res);
  }
}
