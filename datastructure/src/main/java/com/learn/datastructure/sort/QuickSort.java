package com.learn.datastructure.sort;

/**
 * @ClassName MyQuickSort
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/3/20 21:58
 */
public class QuickSort implements Sort {

    /**
     * @param arr
     */
    //[51, 59, 50, 0, 43, 7]
    @Override
    public void sort(int[] arr) {

        doSort(arr, 0, arr.length - 1);
    }

    public void doSort(int[] arr, int from, int to) {
        if (from >= to) {
            return;
        }
        //选定一个基数 从两端向中间遍历 将左边比基数大和元素和右边比基数小的元素对调 直至两边的游标碰头
        int left = from + 1, right = to;
        int base = arr[from];
        while (true) {

            if (left == right) {
                if (arr[left]>arr[right]) {
                    swap(arr, from, left);
                }
                break;
            }

            //右边开始往左遍历
            while (true) {
                if (arr[right] > base) {
                    if (left == right) {
                        break;
                    }
                    right--;

                } else {
                    break;
                }

            }

            while (true) {
                if (arr[left] < base) {
                    if (left == right) {
                        break;
                    }
                    left++;
                } else {
                    break;
                }
            }

            //对调两个元素

            if (left == right) {
                swap(arr, from, left);

                break;
            } else {
                swap(arr, left, right);
            }
        }

        //左边
        doSort(arr, from, Math.max(0, left - 1));

        //右边
        doSort(arr, Math.min(to, right + 1), to);
    }




    public static void main(String[] args) {
        QuickSort sort = new QuickSort();
//        int[] arr = ArrayRandomUtils.random(5, 100);
        int[] arr = new int[]{6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
//        int[] arr = new int[]{6, 2, 7,  3, 5, 10};
//        int[] arr = new int[]{6, 5, 4, 3};
        sort.print(arr);
        sort.sort(arr);
//        sort.print(arr);
    }


}
