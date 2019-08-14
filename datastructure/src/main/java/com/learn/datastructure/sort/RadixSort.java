package com.learn.datastructure.sort;

import com.learn.datastructure.util.ArrayRandom;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/8/14 10:30
 */
public class RadixSort implements Sort {

    @Override
    public void sort(int[] arr) {
        sort(arr, 10);
    }

    public void sort(int[] arr, int radix) {

        if (arr.length < 2) {
            return;
        }
        int length = maxLenght(arr, radix);

        int base = 1;// 例 radix=10  1, 10, 100, 1000 ...
        int buketCount = radix * 2 - 1;//桶数量，考虑负数 例 radix=10 则桶代表的是 -9,-8...,8,9
        int indexInr = radix - 1;//0 - (-(radix - 1))
        int[] count = new int[buketCount];//记录每个桶元素的个数，用于桶扩容和回填到原数组时终止条件
        //TODO 需要 10*N倍空间。。。。。 这里做个简单的优化 先平均一下 如果不够了 再扩容
        int[][] buckets = new int[buketCount][(arr.length + buketCount - 1) / buketCount];
        for (int i = 0; i < length; i++) {//一位比较轮回 例 radix=10 先比较个位 十位 百位 千位。。。。
            for (int n : arr) {//遍历数组，将每个元素放入到各自的桶内
                int m = (n / base) % radix;
                if (count[m + indexInr] >= buckets[m + indexInr].length) {
                    System.err.println("扩容啦");
                    int[] newBucket = new int[count[m + indexInr] + (count[m + indexInr] << 1)];
                    System.arraycopy(buckets[m + indexInr], 0, newBucket, 0, buckets[m + indexInr].length);
                    buckets[m + indexInr] = newBucket;
                }
                buckets[m + indexInr][count[m + indexInr]++] = n;

            }
            int[] bucket;
            int ai = 0;
            //遍历每一个桶，按照先进先出(保证了排序的稳定性,后一轮不会否定前一轮的结论)的顺序回填到原数组，此轮结束
            for (int ii = 0; ii < buketCount; ii++) {
                bucket = buckets[ii];
                for (int iii = 0; iii < count[ii]; iii++) {
                    arr[ai++] = bucket[iii];
                }
                count[ii] = 0;
            }
            base *= radix;
        }
    }

    /**
     * 找出最大位数
     *
     * @param arr   原数组
     * @param radix 进制，例，10，则返回的位数是绝对值最大的位数
     * @return
     */
    private int maxLenght(int[] arr, int radix) {
        int positiveMax = 0;
        int negativeMin = 0;
        for (int n : arr) {
            if (n > positiveMax) {
                positiveMax = n;
            } else if (n < negativeMin) {
                negativeMin = n;
            }

        }

        //计算位数
        int length = 0;
        int max = positiveMax > -1 * negativeMin ? positiveMax : -1 * negativeMin;
        while (max > 0) {
            length++;
            max = max / radix;
        }
        return length;
    }

    /**
     * @param x
     * @param y
     * @return x^y
     */
    private int pow(int x, int y) {
        int r = 1;
        for (int i = 0; i < y; i++) {
            r *= x;
        }
        return r;
    }

    public static void main(String[] args) {
        RadixSort sort = new RadixSort();
//        int[] arr = ArrayRandom.random(90000000, -900000, 900000);
//        sort.print("origin", arr);
        int[] arr = {84, 75, -82, -1, -40, 1, 4, 7};
        sort.sort(arr);
        sort.print("sorted", arr);

        System.err.println();


    }
}
