package com.learn.datastructure.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MapLearn
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 11/11/2018 20:14
 */
public class MapLearn {

    public static void main(String[] args) {
        ConcurrentHashMap<Integer,String> concurrentHashMap=new ConcurrentHashMap<>();
        concurrentHashMap.put(null,"null");
        System.err.println( concurrentHashMap.keys());
    }



}
