package com.learn.datastructure.sort;

/**
 * @ClassName BubbleSort
 * @Description 冒泡排序
 * @Author xianjun@ixiaopu.com
 * @Date 21/05/2018 22:02
 */
public class BubbleSort implements Sort {


    /**
     * 时间复杂度O(N^2)
     * 空间复杂度O(1)
     *
     * @param arr
     */
    @Override
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }


    }

    public static void main(String[] args) {
        int[] arr = {5, 18, 90, 73, 58};
        Sort sort = new BubbleSort();
        sort.sort(arr);
        sort.print(arr);

    }


}
