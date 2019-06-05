package com.learn.datastructure.sort;

/**
 * @ClassName BubbleSort
 * @Description 冒泡排序
 * @Author xianjun@ixiaopu.com
 * @Date 21/05/2018 22:02
 */
public class BubbleSort implements Sort {


    @Override
    public void sort(int[] arr) {
        if (arr != null && arr.length > 1) {
            int tmp;
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = 0; j < arr.length - 1 - i; j++) {
                    if (arr[j] > arr[j + 1]) {
                        tmp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = tmp;
                    }
                }

            }
        }
    }


}
