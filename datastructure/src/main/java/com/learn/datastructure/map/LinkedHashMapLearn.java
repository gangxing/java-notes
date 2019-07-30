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
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>(3);
        map.put(1,"Value1");

        String value=map.get(2);
        System.err.println(value);

    }
}
