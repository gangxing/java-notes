package com.learn.datastructure.sort;

/**
 * @ClassName MyQuickSort
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/3/20 21:58
 */
public class QuickSort implements Sort {

  public static void main(String[] args) {
    QuickSort sort = new QuickSort();
//        int[] arr = ArrayRandomUtils.random(5, 100);
//        int[] arr = new int[]{6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
//        int[] arr = new int[]{6, 2, 7,  3, 5, 10};
    int[] arr = new int[]{6, 11, 3, 9, 8};
    sort.print(arr);
    sort.sort(arr);
    sort.print(arr);
  }

  /**
   * @param arr
   */
  //[51, 59, 50, 0, 43, 7]
  @Override
  public void sort(int[] arr) {
    sort(arr, 0, arr.length - 1);
  }

  private void sort(int[] arr, int from, int to) {
    if (from == to) {
      return;
    }
    int pivotIndex = partition(arr, from, to);
    if (pivotIndex > from) {
      sort(arr, from, pivotIndex - 1);
    }
    if (pivotIndex < to) {
      sort(arr, pivotIndex + 1, to);
    }
  }

  private int partition(int[] arr, int from, int to) {
    int pivotIndex = from;
    //以最后一个元素为分区点
    int pivot = arr[to];
    for (int j = from; j < to; j++) {
      if (arr[j] < pivot) {
        swap(arr, pivotIndex, j);
        pivotIndex++;
      }
    }
    swap(arr, pivotIndex, to);
    return pivotIndex;

  }


}
