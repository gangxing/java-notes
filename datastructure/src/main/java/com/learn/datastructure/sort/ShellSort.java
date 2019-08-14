package com.learn.datastructure.sort;

/**
 * @ClassName ShellSort
 * @Description 希尔排序
 * @Author xgangzai@gmail.com
 * @Date 2019/6/4 20:24
 */
public class ShellSort implements Sort {

    /**
     * 先将整个待排序的序列分割成若干个子序列分别进行直接插入排序，待整个序列中基本有序后，再整体一次进行直接插入排序
     * 将整个待排序序列按照2的倍数均分成段，对每段进行直接插入排序，直至段的长度为1
     * 跟直接插入排序相比，希尔排序有较大的改进
     *
     * 时间复杂度 O(n*log(n)^2)
     * 最好的比较排序
     *
     */
    @Override
    public void sort(int[] arr) {

       iteratorShellSort(arr);


    }


    private void iteratorShellSort(int[] arr){
        for (int i = 2; i < 2 * arr.length; i <<= 1) {

        }
    }


}
