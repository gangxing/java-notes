package com.learn.datastructure.collection;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @ClassName ArrayListLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/8 22:20
 */
public class ArrayListLearn {

  static ArrayList<String> list = new ArrayList<>(2);

  public static void main(String[] args) {
    for (int i = 1; i < 4; i++) {
      list.add("el" + i);
    }

    print();

    list.removeIf((s) -> s.equals("el2"));
//        list.removeIf(new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return "el2".equals(s);
//            }
//        });

    //这样删除以前貌似会报并发错误，原因见https://blog.csdn.net/wangjun5159/article/details/61415358
    //但是在java8中，可以正常工作，所以foreach的实现原理是什么
//        for (String el:list){
//            if ("el2".equals(el)) {
//                list.remove(el);
//            }
//        }

    Iterator<String> iterator = list.iterator();
    while (iterator.hasNext()) {
      String next = iterator.next();
      if ("el2".equals(next)) {
        iterator.remove();
      }
    }

    print();
  }

  private static void print() {
    System.err.println("size:" + list.size());
    for (Object o : list) {
      System.err.println(o);
    }
  }
}
