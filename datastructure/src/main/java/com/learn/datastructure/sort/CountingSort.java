package com.learn.datastructure.sort;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/8/14 10:29
 */
public class CountingSort implements Sort {

    /**
     * 核心思想在于元素值和下标的互换 有点类似于倒排(反向)索引的意思
     * 从而得出元素在排序后，从头到自己的元素个数，即排在第几位
     *
     * @param a
     */
    @Override
    public void sort(int[] a) {
        int min = a[0];
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            } else if (a[i] < min) {
                min = a[i];
            }
        }

        //TODO 严重依赖具体的值， 不适合大跨度的数据 比如有个0 有个Integer.MAX_VALUE 立马OOM
        int[] c = new int[max - min + 1];

        //原数组中值为i+min的元素的个数
        for (int e : a) {
            int i = e - min;
            c[i]++;
        }

        print(c);

        //这种方式理解起来更直观，也少一次遍历吧
        /*int index = 0;
        for (int i = 0; i < newArr.length; i++) {
            if (newArr[i] != 0) {
                for (int i1 = 0; i1 < newArr[i]; i1++) {
                    arr[index++] = i+min;
                }

            }

        }*/

        //下面这种方式貌似是官方的实现方式 但是理解起来很费劲
        int[] b = new int[a.length];
        //c[i]代表原数组中[min,i+min]的元素个数，即c[i]-1就是值i+min排序后的下标
        for (int i = 1; i < c.length; ++i) {
            c[i] = c[i] + c[i - 1];
        }
        print("c", c);
        for (int i = a.length - 1; i >= 0; --i) {
            int k = a[i] - min;
            //这里--很巧妙 有两个目的
            //1 c[k]代表原数组中i+min(a[i])这个元素前面包括自己总的元素个数，即c[k]-1才是值i+min排序后的下标
            //2 如果i+min这个元素有重复，再第一次取出后，c[k]了1，这个元素前面包括自己的个数就减少了一个，即再向前一位放置
            b[--c[k]] = a[i];//按存取的方式取出c的元素
            print("c", c);
            print("b", b);
        }


        System.arraycopy(b, 0, a, 0, a.length);


    }


    public static void main(String[] args) {
        CountingSort sort = new CountingSort();
        int[] arr = {8, 3, 1, 9, 7, 4, 5, 7};
        sort.sort(arr);
        sort.print(arr);

    }
}
