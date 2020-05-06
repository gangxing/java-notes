package com.learn.datastructure.sort;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/11/22 13:43
 */
public class BucketSort implements Sort {

  public static void main(String[] args) {
    BucketSort sort = new BucketSort();

    int[] arr = new int[]{18, 23, 9, 45, 87, 19};

    sort.print(arr);

    sort.sort(arr);

    sort.print(arr);
  }

  /**
   * 核心思想， 1.将元素按照某种规则分配到各个桶中 2.对桶内元素排序(快排？) 3.依次从各个桶取出元素
   *
   * @param arr
   */
  @Override
  public void sort(int[] arr) {
    int len = arr.length;
    int[][] buckets = new int[10][len];

  }
}
