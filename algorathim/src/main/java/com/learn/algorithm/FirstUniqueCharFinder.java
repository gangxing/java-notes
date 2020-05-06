package com.learn.algorithm;

/**
 * @ClassName FirstUniqueCharFinder
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/3/19 23:38
 */
public class FirstUniqueCharFinder {

  /**
   * find the first unique char of specified string
   *
   * @param s
   * @return
   */
  public char find(String s) {
    char[] chars = s.toCharArray();
    char[] excludeChars = new char[26];
    int index = 0;
    outter:
    for (int i = 0; i < chars.length; i++) {
      //compared char
      char c = chars[i];
      for (char ec : excludeChars) {
        if (ec == c) {
          continue outter;
        }
      }
      boolean duplicate = false;
      for (int j = 0; j < chars.length; j++) {
        if (c == chars[j] && i != j) {
          duplicate = true;
          excludeChars[index++] = c;
          break;
        }
      }

      if (!duplicate) {
        return c;
      }


    }

    return (char) -1;
  }
}
