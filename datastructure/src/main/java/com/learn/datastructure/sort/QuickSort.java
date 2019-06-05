package com.learn.datastructure.sort;


/**
 * @ClassName QuickSorter
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 21/05/2018 22:00
 */
public class QuickSort implements Sort {

    /**
     * 基本思想：选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,
     * 一部分比基准元素小,一部分大于等于基准元素,此时基准元素在其排好序后的正确位置,然后再用同样的方法
     * 递归地排序划分的两部分。
     * <p>
     * todo 妈的 完全看不懂啊
     *
     * @param arr
     */
    @Override
    public void sort(int[] arr) {
        if (arr != null && arr.length > 1) {
            qsort(arr, 0, arr.length - 1);
        }
    }

    private static void qsort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);        //将数组分为两部分
            qsort(arr, low, pivot - 1);                   //递归排序左子数组
            qsort(arr, pivot + 1, high);                  //递归排序右子数组
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[low];     //枢轴记录
        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                --high;
            }
            arr[low] = arr[high];             //交换比枢轴小的记录到左端
            while (low < high && arr[low] <= pivot) {
                ++low;
            }
            arr[high] = arr[low];           //交换比枢轴小的记录到右端
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        //返回的是枢轴的位置
        return low;
    }
}
