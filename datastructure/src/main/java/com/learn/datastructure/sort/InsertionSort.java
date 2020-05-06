package com.learn.datastructure.sort;

/**
 * @ClassName InsertionSort
 * @Description 直接插入排序
 * @Author xgangzai@gmail.com
 * @Date 2019/6/4 19:26
 */
public class InsertionSort implements Sort {

  public static void main(String[] args) {
    int[] arr = {4, 5, 3, 7, 2, 1};

    Sort sort = new InsertionSort();
    sort.sort(arr);
    sort.print(arr);
  }

  /**
   * 实现原理 从第一个元素开始作为已排序的子集，取下一个元素加入到已排序子集中排序的位置，直至遍历完所有的元素 空间复杂度O(1) 时间复杂度O(n^2)
   * 这是最直观的排序，就像打麻将，起手全是万子，或者斗地主，怎么将牌理顺
   */
  @Override
  public void sort(int[] arr) {

  }

  private void sort1(int[] arr) {
    for (int index = 1; index < arr.length; index++) {
      int i = index - 1;
      while (i >= 0 && arr[i] > arr[i + 1]) {
        swap(arr, i, i + 1);
        i--;
      }
    }
  }

  private void sort2(int[] arr) {
    for (int index = 1; index < arr.length; index++) {
      int value = arr[index];
      int i = index - 1;
      while (i >= 0) {
        if (arr[i] > value) {
          //向后移
          arr[i + 1] = arr[i];
          i--;
        } else {
          break;
        }
      }
      arr[i] = value;
    }
  }

}
