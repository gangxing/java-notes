package com.learn.datastructure.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName MapLearn
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 11/11/2018 20:14
 */
public class HashMapLearn {

    public static void main(String[] args) {

        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "v1");
        map.put(2, "v2");
        Set<Map.Entry<Integer,String>> entrySet=map.entrySet();
        Iterator<Map.Entry<Integer,String>> iterator=entrySet.iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer,String> nextEntry=iterator.next();
            System.err.println(nextEntry.getKey()+"->"+nextEntry.getValue());
        }
    }


}
