package com.learn.algorithm;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/12/2 17:08
 */

/**
 * 题目描述：给定一串字符，里面有些字符有连续出现的特点，请寻找这些连续出现字符中最长的串， 如果最长的串有多个，请输出字符ASCII码最小的那一串。
 * 例如：输入aaabbbbbcccccccczzzzzzzz，输出cccccccc。
 */
public class FindMaxContinueSubString {

  public static void main(String[] args) {

    String s = "aaabbbbbccccccccczzzzzzzz";
    String ss = find(s);
    System.err.println(ss);
  }

  private static String find(String s) {
    //用长度为2的数组存储字符串ASCII值和长度。
    //两个数组，一个存当前的，一个存历史最长的
    int[] history = new int[2];
    int[] current = new int[2];
    char[] chars = s.toCharArray();
    for (char c : chars) {
      if (current[0] == 0 || current[0] == c) {
        current[0] = c;
        current[1]++;
      } else {
        if (current[1] > history[1] || (current[1] == history[1] && current[0] > history[0])) {
          history[0] = current[0];
          history[1] = current[1];
          current[0] = c;
          current[1] = 1;
        }
      }
    }

    int[] res = history;
    if (current[1] > history[1]) {
      res = current;
    } else if (current[1] == history[1] && current[0] > history[0]) {
      res = current;
    }

    char[] chars1 = new char[res[1]];
    for (int i = 0; i < chars1.length; i++) {
      chars1[i] = (char) res[0];
    }

    return new String(chars1);
  }
}
