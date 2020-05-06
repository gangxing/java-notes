package com.learn.datastructure.map;

import java.util.TreeMap;

/**
 * @ClassName TreeMapLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/16 15:10
 */
public class TreeMapLearn {

  public static void main(String[] args) {
    TreeMap<Object, String> treeMap = new TreeMap<>();

    treeMap.put(new Object(), "value40");
    treeMap.put(new Object(), "value50");
    treeMap.put(30, "value30");
    treeMap.put(20, "value20");
    treeMap.values();
  }
}
