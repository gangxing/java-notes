package com.learn.datastructure.sort;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/8/12 17:01
 */
public class ArraysLearn {

    public static void main(String[] args) {

//        Integer[] arr = {3, 5, 2, 9, 0, 10};
//        List<Integer> list = Arrays.asList(arr);
//
//        System.err.println(list instanceof ArrayList);
        sortString();

    }

    private static void sortString(){
        String[] a={"Ab","Cc","Mike","Lily"};
        Arrays.sort(a);
        int[] arr=new int[]{4,8,22,0,1};
        Arrays.sort(arr);
        System.err.println(Arrays.toString(a));

    }
}
