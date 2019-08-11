package com.learn.datastructure.sort;

/**
 * @ClassName InsertionSort
 * @Description 直接插入排序
 * @Author xgangzai@gmail.com
 * @Date 2019/6/4 19:26
 */
public class InsertionSort implements Sort {

    /**
     * 实现原理
     * 从第一个元素开始作为已排序的子集，取下一个元素加入到已排序子集中排序的位置，直至遍历完所有的元素
     * 空间复杂度O(1)
     * 时间复杂度O(n^2)
     */
    @Override
    public void sort(int[] arr) {
        sort(arr, 0, arr.length);
    }

    //只对部分进行直接排序
    public void sort(int[] arr, int position, int length) {
        int index = position + 1;
        while (index < position + length) {
            int e = arr[index];
            //找到当前元素在已排序的子集中的位置
            for (int i = position; i < index; i++) {
                if (e >= arr[i]) {
                    continue;
                }
                //将i~index-1这几个元素往后移一位
//                for (int j = index - 1; j >= i; j--) {
//                    arr[j + 1] = arr[j];
//                }
                System.arraycopy(arr, i, arr, i + 1, index - i);
                arr[i] = e;
                break;
            }
            index++;
        }
    }


//    public void sort(int[] arr) {
//        if (arr == null || arr.length <= 1) {
//            return;
//        }
//        int index = 1;
//        while (index < arr.length) {
//            int e = arr[index];
//            //找到当前元素在已排序的子集中的位置
//            for (int i = 0; i < index; i++) {
//                if (e >= arr[i]) {
//                    continue;
//                }
//                //将i~index-1这几个元素往后移一位
////                for (int j = index - 1; j >= i; j--) {
////                    arr[j + 1] = arr[j];
////                }
//                System.arraycopy(arr, i, arr, i + 1, index - i);
//                arr[i] = e;
//                break;
//            }
//            index++;
//        }
//    }


}
