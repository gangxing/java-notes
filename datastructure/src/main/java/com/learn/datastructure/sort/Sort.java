package com.learn.datastructure.sort;

import java.util.Arrays;

/**
 * @ClassName Sorter
 * @Description 实现常用的八种排序算法 并计算时间复杂度 空间复杂度
 * 在计算机科学中log(N)如果没有特别说明，都是指数学意义上的log2(N)=lgN/lg2
 * @Author xianjun@ixiaopu.com
 * @Date 21/05/2018 21:59
 */
public interface Sort {

    /**
     * 升序排序
     *
     * @param arr
     */
    void sort(int[] arr);

    default void print(String prefix, int[] arr) {
        System.err.println(prefix + " " + Arrays.toString(arr));
    }

    default void print(int[] arr) {
        print("", arr);
    }

    default void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
