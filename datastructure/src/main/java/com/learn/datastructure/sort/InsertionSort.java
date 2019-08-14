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
     * 这是最直观的排序，就像打麻将，起手全是万子，或者斗地主，怎么将牌理顺
     */
    @Override
    public void sort(int[] arr) {
        sort(arr, 0, arr.length);
    }

    //只对部分进行直接排序
    public void sort(int[] arr, int position, int length) {
        for (int index = position + 1; index < position + length; index++) {
            int e = arr[index];
            //找到当前元素在已排序的子集中的位置
//            for (int i = position; i < index; i++) {
//                if (e >= arr[i]) {
//                    continue;
//                }
//                System.arraycopy(arr, i, arr, i + 1, index - i);
//                arr[i] = e;
//                break;
//            }

            //往前遍历
            for (int i = index - 1; i >= position; i--) {
                if (e <arr[i]) {
                    swap(arr, i,i+1);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 5,3,7, 2, 1};

        Sort sort = new InsertionSort();
        sort.sort(arr);
        sort.print(arr);
    }

}
