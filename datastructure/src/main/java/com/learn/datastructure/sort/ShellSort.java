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
     * 这一点跟归并排序的迭代方式类似，可以分为递归和迭代两种方式实现
     * 后者是将两个段合并起来 需要借助额外的存储空间，空间复杂度O(N)
     * TODO 前者应该不需要额外存储空间 但是要移动 所以时间复杂度比后者更高？
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


    /**
     * java 值传递，引用传递
     * @see <link>https://juejin.im/post/5bce68226fb9a05ce46a0476</link>
     *
     */


}
