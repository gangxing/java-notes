package com.learn.datastructure.sort;

import java.util.Arrays;

/**
 * @ClassName HeapSort
 * @Description
 * @Author xgangzai@gmail.com
 * @Date 2019/8/5 08:18
 */
public class HeapSort implements Sort {


  public static void main(String[] args) {
    int[] arr = {9, 9, 18, 7, 63, 83, 20, 21, 10, 38};
    Sort sort = new HeapSort();
    sort.sort(arr);
    System.err.println(Arrays.toString(arr));
  }

  @Override
  public void sort(int[] arr) {
    buildHeap(arr);

    for (int i = arr.length - 1; i > 0; i--) {
      swap(0, i, arr);
      //从叶子层到第二层已经是堆结构,只是此时的根节点不是最大的，所以要将根节点往下沉 放置到合理的位置
      siftDown(arr, i - 1, 0);
    }
  }

  private void buildHeap(int[] arr) {
    for (int pi = (arr.length >> 1) - 1; pi >= 0; pi--) {
      siftDown(arr, arr.length - 1, pi);
    }
  }

  /**
   * 在指定范围内，将父节点按照大顶堆的性质往下沉:父节点大于子节点，如果父节点小于子节点，下沉
   *
   * @param arr 源数组
   * @param end 调整的范围，最后一个节点的下标
   * @param pi  父节点下标
   */
  private void siftDown(int[] arr, int end, int pi) {
    int ci = (pi << 1) + 1;
    if (ci > end) {//左节点已超出范围，说明当前"父节点"已经不是真正的父节点了
      return;
    }
    //如果右节点存在并且比左节点大，右节点当做真正的子节点
    if (ci + 1 <= end && arr[ci + 1] > arr[ci]) {
      ci += 1;
    }

    if (arr[pi] < arr[ci]) {
      swap(pi, ci, arr);
      //原来的父节点落到ci子节点上，继续将ci当做新的父节点往下沉
      siftDown(arr, end, ci);
    }
  }

  private void swap(int i, int j, int[] arr) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
