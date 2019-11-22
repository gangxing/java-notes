Merging Sort - 归并排序

**需求**

对N个整数升序排序

**思路**

把数组分成两部分（递归均分，直至每部分只有一个元素），每部分先排序，然后合并有序的两部分。利用到了分治思想，一般来讲，分治思想需要递归编程来实现。

**算法评判**

* 时间复杂度

  $O(NlogN)$

* 空间复杂度

  $O(N)$

  > 只需要常量级的辅助空间，所以也叫原地排序

* 稳定性

  结论：**稳定**

实现代码如下

```java
public void sort(int[] arr) {
    int[] result = new int[arr.length];
    mergeRecursive(arr, result, 0, arr.length - 1);
}

//递归
private void mergeRecursive(int[] arr, int[] result, int start, int end) {
    if (start >= end) {
        return;
    }

    //对半分
    int len = end - start;
    int middle = len >> 1;
    int start1 = start;
    int end1 = start1 + middle;
    int start2 = end1 + 1;
    mergeRecursive(arr, result, start1, end1);
    mergeRecursive(arr, result, start2, end);

    //合并两个组到result中
    int k = start;
    while (start1 <= end1 && start2 <= end) {
        if (arr[start2] < arr[start1]) {
            result[k++] = arr[start2++];
        } else {
          //如果后部分不小于前部分，可能的情况是两个元素相等，优先取前部分的
            result[k++] = arr[start1++];
        }
    }

    //拷贝剩余的
    if (start1 <= end1) {
        System.arraycopy(arr, start1, result, k, end1 + 1 - start1);
    }
    if (start2 <= end) {
        System.arraycopy(arr, start2, result, k, end + 1 - start2);
    }

    //将合并好的拷贝到原数组中
    System.arraycopy(result, start, arr, start, end + 1 - start);

}
```

关于稳定性，因为在合并两个有序数组时，当两个元素相等，优先取前部分中的元素，所以归并排序是稳定的。

这种思路是从上而下，还有一种自下而上的思路，从每个子序只有一个元素开始，每两两合并，直至最终合并成一个序列，代码实现如下

```java
private void sortMerge(int[] arr) {
    int[] orderedArr = new int[arr.length];
    //i 每组数量
    for (int i = 2; i < arr.length * 2; i *= 2) {
        //j 组数
        for (int j = 0; j < (arr.length + i - 1) / i; j++) {
            int left = i * j;
            //mid 是右半部分的第一个元素下标
            int mid = left + i / 2 >= arr.length ? (arr.length - 1) : (left + i / 2);
            int right = i * (j + 1) - 1 >= arr.length ? (arr.length - 1) : (i * (j + 1) - 1);
            int start = 0, l = left, m = mid;
            //因为mid是右半部分的第一个元素下标，所以等号在右半部分
            while (l < mid && m <= right) {
                if (arr[m] < arr[l]) {
                    orderedArr[start++] = arr[m++];
                } else {
                  //如果相等，优先取前部分的元素，保证稳定性
                    orderedArr[start++] = arr[l++];
                }
            }
            while (l < mid) {
                orderedArr[start++] = arr[l++];
            }
            while (m <= right) {
                orderedArr[start++] = arr[m++];
            }
            System.arraycopy(orderedArr, 0, arr, left, right - left + 1);

        }
    }
}
```



> 问题：时间复杂度计算





