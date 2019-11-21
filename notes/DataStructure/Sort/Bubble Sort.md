Bubble Sort-冒泡排序

**需求**

对N个整数升序排序

**思路**

进行N轮排序，每一轮选出最大的元素，在每轮对比中，相邻元素比较，如果前面元素大于后面元素，则交换两个元素位置

需要比对的次数$(N-1)+(N-2)+...+1 =N*(N-1)/2$

**算法评判**

* 时间复杂度

  $O(N^2)$

* 空间复杂度

  $O(1)$

  > 只需要常量级的辅助空间，所以也叫原地排序

* 稳定性

  如果不互换两个相等元素，则是稳定的



实现代码如下

```java
public void sort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr.length - 1 - i; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(arr, j, j + 1);
            }
        }
    }
}
```

优化，当某一轮评选下来发现没有元素交换，说明已经有序了，可以提前结束排序。优化后代码如下

```java
public void sort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      boolean sorted=true;
        for (int j = 0; j < arr.length - 1 - i; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(arr, j, j + 1);
              	sorted=false;
            }
        }
      if(sorted){
        break;
      }
    }
}
```

