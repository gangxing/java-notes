package com.learn.algorithm.charactersmatch;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/23 10:24
 */
public class BFMatcher implements Matcher {

  /**
   * 暴力匹配 Brute Force
   *
   * @param source
   * @param target
   * @return
   * @see String#contains(CharSequence) 时间复杂度O((n-m)*m) => O(n*m) 因为实际应用中，两者的长度都不会很长，所以这种暴力方式也能很快比对完成，并且因为其逻辑简单，
   * 容易将bug暴露出来并且解决，所以被大量使用
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
    for (int i = 0; i <= sourceLen - targetLen + 1; i++) {
      boolean matches = true;
      for (int j = 0; j < targetLen; j++) {
        if (sourceChars[i + j] != targetChars[j]) {
          matches = false;
          break;
        }
      }
      if (matches) {
        return i;
      }
    }
    return -1;
  }
}
