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
            if (arr[start2] < arr[start1]) {
                result[k++] = arr[start2++];
            } else {
                //如果后部分不小于前部分，可能的情况是两个元素相等，优先取前部分的
                result[k++] = arr[start1++];
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
        int[] orderedArr = new int[arr.length];
        //i 每组数量
        for (int i = 2; i < arr.length * 2; i *= 2) {
            //j 组数
            for (int j = 0; j < (arr.length + i - 1) / i; j++) {
                int left = i * j;
                //mid 是右半部分的第一个元素下标
                int mid = left + i / 2 >= arr.length ? (arr.length - 1) : (left + i / 2);
                int right = i * (j + 1) - 1 >= arr.length ? (arr.length - 1) : (i * (j + 1) - 1);
                int start = 0, l = left, m = mid;
                //因为mid是有半部分的第一个元素下标，所以等号在右半部分
                while (l < mid && m <= right) {
                    if (arr[l] < arr[m]) {
                        orderedArr[start++] = arr[l++];
                    } else {
                        orderedArr[start++] = arr[m++];
                    }
                }
                while (l < mid) {
                    orderedArr[start++] = arr[l++];
                }
                while (m <= right) {
                    orderedArr[start++] = arr[m++];
                }
                System.arraycopy(orderedArr, 0, arr, left, right - left + 1);

            }
        }


    }


    public static void main(String[] args) {


        MergingSort sort = new MergingSort();
        int[] arr1 = {5, 18, 90};
        int[] arr2 = {58, 3, 73, 21, 76, 77, 0};
        sort.sortMerge(arr2);
        sort.print(arr2);

    }
}
