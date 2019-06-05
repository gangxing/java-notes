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
     */
    @Override
    public void sort(int[] arr) {
        int round = 1;
        int segment = 2 << round;
        int segmentSize = arr.length / segment;
        while (segmentSize>1){
            InsertionSort sort=new InsertionSort();
            sort.sort(arr,);
            round++;
        }



    }


    /**
     * java 值传递，引用传递
     * @see <link>https://juejin.im/post/5bce68226fb9a05ce46a0476</link>
     *
     */





}
