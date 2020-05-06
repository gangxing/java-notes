package com.learn.algorithm;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/12/2 17:46
 */

import java.util.ArrayDeque;

/**
 * 100以内的加减法
 */
public class Calculator {

  public static void main(String[] args) {
    String s = "12+98-10+20-88+100";

    int res = calculate(s);
    System.err.println(res);
  }


  private static int calculate(String s) {

    if (s == null || s.length() < 1) {
      throw new IllegalArgumentException("");
    }

    ArrayDeque<Integer> numStack = new ArrayDeque<>();
    ArrayDeque<Character> operatorStack = new ArrayDeque<>();
    char[] nums = new char[3];
    int numCount = 0;
    for (char c : s.toCharArray()) {
      if (c == '+' || c == '-') {
        operatorStack.push(c);

        Integer num = Integer.parseInt(new String(nums, 0, numCount));
        numStack.push(num);
        nums = new char[3];
        numCount = 0;
      } else if (c >= '0' && c <= '9') {
        nums[numCount] = c;
        numCount++;
      } else {
        throw new IllegalArgumentException("not number");
      }
    }

    Integer i = Integer.parseInt(new String(nums, 0, numCount));
    numStack.push(i);

    if (numStack.size() - operatorStack.size() != 1) {
      throw new IllegalArgumentException();
    }

    while (operatorStack.size() > 0) {
      int left = numStack.pollLast();
      Character character = operatorStack.pollLast();
      int right = numStack.pollLast();
      if ('+' == character) {
        numStack.offerLast(left + right);
      } else {
        numStack.offerLast(left - right);
      }
    }

    return numStack.pop();
  }

}
