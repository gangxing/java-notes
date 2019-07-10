package com.learn.datastructure.collection;

import java.util.*;

/**
 * @ClassName MyHashSet
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/8 21:55
 */
public class MyHashSet {

    public static void main(String[] args) {
        Set<Object> set=new HashSet<>(2);
        set.add(new Object());
        set.add(new Object());
        set.add(null);
        set.add(null);
        for (Object o:set){
            System.err.println(o);
        }

        Map<Object,Object> map=new HashMap<>(3);
        map.put(null,null);
        for (Map.Entry<Object,Object> entry:map.entrySet()) {
            System.err.println(entry.getKey()+"-->"+entry.getValue());
        }
    }
}
