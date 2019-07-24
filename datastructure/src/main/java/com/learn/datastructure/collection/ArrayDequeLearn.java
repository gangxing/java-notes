package com.learn.datastructure.collection;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @ClassName ArrayDequeLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/7/24 17:04
 */
public class ArrayDequeLearn {

    public static void main(String[] args) {
        ArrayDeque<Integer> stack=new ArrayDeque<>(3);

        for (int i=10;i>0;i--) {
            stack.push(i);
        }
        while (!stack.isEmpty()){
            Integer top=stack.pop();
            System.err.println(top);

        }
        stack.iterator();
//        Stream<Integer> stream=stack.stream();
//        Optional<Integer> optional=stream.findAny();
    }
}
