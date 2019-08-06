package com.learn.datastructure.sort;

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


    public static void main(String[] args) {
        SelectionSort sort = new SelectionSort();
        int[] arr1 = {5, 18, 90};
        int[] arr2 = {58, 3, 73, 21, 76, 77, 0};
        sort.sort(arr2);
        sort.print(arr2);
    }
}
