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
     * <p>
     * 时间复杂度 O(n*(logn)^2)
     * 最好的比较排序
     */
    //田径赛马 每一轮的排序中，都相当于一场完整的田径赛马，各段依次拿出自己的第j号参与排序
    @Override
    public void sort(int[] a) {
        int step = a.length >> 1;
        while (step > 0) {
            sortByStep(a, step);
            step >>= 1;
        }
    }

    public void sort3(int[] a) {
        int number = a.length / 2;
        int i;
        int j;
        int temp;
        while (number >= 1) {
            for (i = number; i < a.length; i++) {
                temp = a[i];
                j = i - number;
                while (j >= 0 && a[j] > temp) { //需要注意的是，這裡array[j] < temp將會使數组從大到小排序。
                    a[j + number] = a[j];
                    j = j - number;
                }
                a[j + number] = temp;
            }
            number = number / 2;
        }
    }

    public void sort2(int[] a) {

        //步长
        int gap = a.length >> 1;
        for (; gap > 0; gap >>= 1) {

            //j 每一段的序号
            for (int j = 0; j + gap < a.length; j++) {

                for (int k = 0; k + gap < a.length; k += gap) {
                    if (a[k] > a[k + gap]) {
                        swap(a, k, k + gap);
                    }
                }
            }

            print(a);


        }
    }

    private void sortByStep(int[] a, int step) {
        for (int i = step; i < a.length; i++) {
            int j = i - step;
            while (j >= 0 && a[j] > a[j + step]) { //需要注意的是，這裡array[j] < temp將會使數组從大到小排序。
                swap(a, j, j + step);
                j = j - step;
            }
        }

    }


    //这种实现方式有点难理解
    public void sort0(int[] a) {

        //每段的元素数量,步长
        int d = a.length;
        while (d > 1) {
            d >>= 1;
            for (int x = 0; x < d; x++) {
                //对每一段进行插入排序
                for (int i = x + d; i < a.length; i = i + d) {
                    int temp = a[i];
                    int j;
                    for (j = i - d; j >= 0 && a[j] > temp; j = j - d) {
                        a[j + d] = a[j];
                    }
                    a[j + d] = temp;
                }
            }
            print(a);
        }
    }

    private void step(int n) {
        int[] fa = new int[n];
        fa[0] = 1;
        fa[1] = 2;
        for (int i = 2; i < n; i++) {
            fa[i] = fa[i - 1] + fa[i - 2];
        }
        print(fa);
        for (int j = 0; j < n; j++) {
            int f = fa[j];
            int ff = (int) Math.pow(f, 1.6180339887498948482 * 2);
            fa[j] = ff;
        }
        print(fa);
    }

    public static void main(String[] args) {
//        int[] arr = {49, 38, 65, 97, 76, 13, 27, 49, 55, 4};
        int[] arr = {49, 38, 65, 97, 4};

        ShellSort sort = new ShellSort();
        sort.print(arr);
        sort.sort(arr);
        sort.print(arr);
//        sort.step(15);

    }


}
