package com.learn.datastructure.collection;

import java.util.ArrayList;

/**
 * @ClassName ListLearn
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/6/12 15:49
 */
public class ListLearn {

    public static void main(String[] args) {
        /*
         * ArrayList
         * 明确size 和 capacity两个概念 以及扩容策略
         *
         */

        int[] arr=new int[5];

        System.err.println(arr.length);//5

        ArrayList<Integer> list=new ArrayList<>(2);
        for (int i=0;i<4;i++) {
            list.add(i);
        }
        System.err.println(list.size());

    }
}
