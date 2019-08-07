package com.learn.datastructure.sort;

/**
 * @ClassName MergingSort
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/8/5 17:52
 */
public class MergingSort implements Sort {

    /**
     * 分治思想 递归法 两两一组 组内有序 然后组减半 直至合并成一个组
     * 一轮 n/2组
     * 二轮 n/4组
     * 三轮 n/8组
     * 。
     * 。
     * 。
     * logn轮 1组
     * 核心思想，两个有序序列的合并
     *
     * @param arr
     */
    @Override
    public void sort(int[] arr) {
        //实现好难啊 关键在于 怎么递归每一轮
        int[] result = new int[arr.length];
        mergeRecursive(arr, result, 0, arr.length - 1);
    }


    //递归
    private void mergeRecursive(int[] arr, int[] result, int start, int end) {
        if (start >= end) {
            return;
        }

        //对半分
        int len = end - start;
        int middle = len >> 1;
        int start1 = start;
        int end1 = start1 + middle;
        int start2 = end1 + 1;
        mergeRecursive(arr, result, start1, end1);
        mergeRecursive(arr, result, start2, end);

        //合并两个组到result中
        int k = start;
        while (start1 <= end1 && start2 <= end) {
            if (arr[start1] < arr[start2]) {
                result[k++] = arr[start1++];
            } else {
                result[k++] = arr[start2++];
            }
        }

        //拷贝剩余的
        if (start1 <= end1) {
            System.arraycopy(arr, start1, result, k, end1 + 1 - start1);
        }
        if (start2 <= end) {
            System.arraycopy(arr, start2, result, k, end + 1 - start2);
        }

        //将合并好的拷贝到原数组中
        System.arraycopy(result, start, arr, start, end + 1 - start);

    }


    //自下而上 两两合并 直至最后合并的序列数量为1
    private void sortMerge(int[] arr) {


    }


    public static void main(String[] args) {


        MergingSort sort = new MergingSort();
        int[] arr1 = {5, 18, 90};
        int[] arr2 = {58, 3, 73, 21, 76, 77, 0};
        sort.sort(arr2);
        sort.print(arr2);

    }
}
