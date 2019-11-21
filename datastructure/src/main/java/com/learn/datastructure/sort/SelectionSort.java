package com.learn.datastructure.sort;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

/**
 * @ClassName SelectionSort
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/8/5 23:44
 */
public class SelectionSort implements Sort {

    /**
     * 跟冒泡排序类似 循环N轮，每次找出一个最值，这一点跟堆排序类似
     * 和冒泡排序异同
     * 相同：比较的次数一样
     * 不同：冒泡在比较相邻两个元素时，如果顺序方向不是预期，则调换之
     * 选择排序，在遍历无序序列时，只是记录最值元素的下标，遍历之后，将最值跟其正确位置上的元素兑换
     * <p>
     * 和插入排序类似，分有序和无序两部分，每一轮从无序中找到最小的元素插入到有序的末尾
     * <p>
     * 时间复杂度O(N^2)
     * 空间复杂度O(1)
     *
     * @param arr
     */
    @Override
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            swap(arr, min, i);
        }
    }


    public void sort(Item[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j].compareTo(arr[min]) < 0) {
                    min = j;
                }
            }
            swap1(arr, min, i);
        }
    }

     void swap1(Item[] arr, int i, int j) {
        Item t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }


    public static void main1(String[] args) {
        SelectionSort sort = new SelectionSort();
        int[] arr1 = {5, 18, 90};
        int[] arr2 = {58, 3, 73, 21, 76, 77, 0};
        sort.sort(arr2);
        sort.print(arr2);
    }

    public static void main(String[] args) {
        SelectionSort sort = new SelectionSort();
        Item[] arr=new Item[5];
        arr[0]=new Item(5,"51");
        arr[1]=new Item(4,"41");
        arr[2]=new Item(5,"52");
        arr[3]=new Item(6,"61");
        arr[4]=new Item(4,"42");
        System.err.println(Arrays.toString(arr));

        sort.sort(arr);
        System.err.println(Arrays.toString(arr));
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Item implements Comparable<Item> {
        private int key;

        private String value;

        @Override
        public int compareTo(Item o) {
            return this.key - o.key;
        }

        @Override
        public String toString() {
            return key + ":" + value;
        }
    }
}
