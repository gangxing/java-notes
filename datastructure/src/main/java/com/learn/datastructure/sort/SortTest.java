package com.learn.datastructure.sort;

import com.learn.datastructure.util.ArrayRandom;

import java.util.Arrays;

/**
 * @ClassName Main
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 21/05/2018 22:53
 */
public class SortTest {

    public static void main(String[] args) {
        /*
         * InsertionSort 180s
         * BubbleSort
         */
        Sort sort = new InsertionSort();
        int[] arr = ArrayRandom.random(5,-10,100);
        System.out.println(Arrays.toString(arr));
        long start=System.currentTimeMillis();
        sort.sort(arr);
        long end=System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("duration "+(end-start)+" ms");
//        int i=4;
//        minusBefore(i);
//        minusAfter(i);
    }

    private static void minusBefore(int i) {
        while (i > 0) {
            System.err.println("before:" + --i);
        }
    }

    private static void minusAfter(int i) {
        while (i > 0) {
            System.err.println("after:" + i--);
        }
    }
}
