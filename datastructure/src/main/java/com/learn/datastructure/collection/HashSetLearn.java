package com.learn.datastructure.collection;

import java.util.HashSet;

/**
 * @ClassName HashSetLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/10 14:27
 */
public class HashSetLearn {

  public static void main(String[] args) {
    HashSet<String> set = new HashSet<>(2);
    set.add(null);
    set.add(null);
    System.err.println(set.size());
  }
}
