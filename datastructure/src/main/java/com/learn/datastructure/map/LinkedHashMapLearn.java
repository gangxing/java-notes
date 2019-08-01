package com.learn.datastructure.map;

import java.util.LinkedHashMap;

/**
 * @ClassName LinkedHashMapLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/16 15:11
 */
public class LinkedHashMapLearn {

    public static void main(String[] args) {
//        LinkedHashMap<Integer, String> map = new LinkedHashMap<>(3, 0.75f, true);
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>(3,0.75f,true);
        map.put(1,"Value1");
        map.put(2,"Value2");
        map.put(3,"Value3");

        String value=map.get(2);
        System.err.println(value);

    }
}
