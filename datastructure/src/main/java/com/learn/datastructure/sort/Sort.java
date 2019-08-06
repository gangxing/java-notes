package com.learn.datastructure.sort;

import java.util.Arrays;

/**
 * @ClassName Sorter
 * @Description 实现常用的八种排序算法 并计算时间复杂度 空间复杂度
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

    default void print(int[] arr) {
        System.err.println(Arrays.toString(arr));
    }

    default void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
