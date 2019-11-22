Sort

经典排序算法，各自的时间复杂度，空间复杂度

[https://itimetraveler.github.io/2017/07/18/%E5%85%AB%E5%A4%A7%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95%E6%80%BB%E7%BB%93%E4%B8%8Ejava%E5%AE%9E%E7%8E%B0/](https://itimetraveler.github.io/2017/07/18/八大排序算法总结与java实现/)



https://mp.weixin.qq.com/s/HQg3BzzQfJXcWyltsgOfCQ



最直观的排序：插入排序



比较排序 N*logN



[这篇文章](https://blog.csdn.net/wuzhiwei549/article/details/80654836)对排序总结的很好

* 时间复杂度

* 空间复杂度

* 稳定性

  何为排序的稳定性，是指两个”相等“的元素在排序后和排序前的相对次序不发生改变。在常见的试验中，都是以整数作为元素，这种情况下，所谓的稳定性不好判断，也感受不到稳定性的意义。但是在实际场景中，一般待排序的元素都是各种对象，按照对象某个字段进行排序，如果两个对象用于排序的字段相等，则在这次排序中，这两个对象是相等的。

  稳定性的意义，后续排序可以借用之前的排序顺序，减少排序运算次数？

  > 举例，一批订单，按照支付金额正序，如果金额相等，则按照下单时间正序。
  >
  > 实现：先按照下单时间正序，再用稳定性排序算法按照支付金额正序
  >
  > 因为排序算法是稳定的，所以遇到金额相同的订单，不再改变他们的次序（这个次序在前一步根据时间已按照下单时间正序）。如果排序算法没有不稳定，金额相等时，会打乱他们之间的时间顺序。

* 各自适用场景

  
  
  
  

这个人的[博客](https://juejin.im/post/5c69fa6cf265da2dc006475c)写得很好，是我参照的目标

|        | 时间复杂度 |空间复杂度 |是否基于比较|稳定性 |
| :------ | :--------- |:--------- |:--------- |:--------- |
| Bubble Sort | $O(N^2)$ |$O(1)$     | 是 |稳定|
| Insertion Sort | $O(N^2)$ |$O(1)$     |是     |稳定|
| Selection Sort | $O(N^2)$ |$O(1)$     |是   | **不稳定** |
| Merging Sort   | $O(NlogN)$ |$O(N)$     |是    | 稳定 |
| Quick Sort     | $O(NlogN)$ |$O(1)$     |是     |**不稳定**|
| Shell Sort |      |     |     |||
| Heap Sort |      |     |     |||
| Radix Sort | $O(N)$ |            |     |||
| Counting Sort | $O(N)$ |     |     |||
| Bucket Sort | $O(N)$ |     |     |||



从时间复杂度来讲，计数排序、基数排序和桶排序貌似是最好的，但是有两个致命的缺陷

1. 对源数据有较高要求，具体来讲利于均衡划分？
2. 空间复杂度高，具体多高？？？

那后面三种有适用的场景吗？



`Arrays`针对不同数据源对算法的选择

参考https://www.cnblogs.com/gw811/archive/2012/10/04/2711746.html

https://juejin.im/entry/5af84ef3f265da0b8f62a53d



对基础类型和对象类型的排序分开处理。因为对象类型排序要求排序算法稳定，所以快速排序不适用。对于基础类型，则选用快速排序（双周快排，优化版的快排）。

在基础类型排序中，从输入数据量大小选择不同的排序算法

```java
/**
     * If the length of an array to be sorted is less than this
     * constant, Quicksort is used in preference to merge sort.
     */
    private static final int QUICKSORT_THRESHOLD = 286;

    /**
     * If the length of an array to be sorted is less than this
     * constant, insertion sort is used in preference to Quicksort.
     */
    private static final int INSERTION_SORT_THRESHOLD = 47;

```

算法选择策略

1. 如果数组元素个数小于`INSERTION_SORT_THRESHOLD`(47),那么使用改进的插入排序进行排序

2. 如果元素个数大于插入排序的阈值并且小于快速排序的阈值QUICKSORT_THRESHOLD(286)，则使用改进的双轴快速排序进行排序

3. 如果元素个数大于快速排序的阈值，根据数组的无序程度来判定继续使用哪种算法，无序程度通过将数组划分为不同的有序序列的个数来判定

   3.1 如果有序序列的个数大于MAX_RUN_COUNT(67),则认为原数组基本无序,则仍然使用双轴快速排序

   3.2 如果小于MAX_RUN_COUNT，则认为原数组基本有序，使用归并排序进行排序。

在数据量小的情况下，为什么时间复杂度$O(N^2)$的插入排序优于快速排序(时间复杂度$O(NlogN)$)，因为快速排序的递归？

在有序度高的情况下，为什么空间复杂度$O(N)$的归并排序优于快速排序(空间复杂度$O(1)$),因为归并排序本意就是将多个有序序列合并成一个有序序列。。。。？

> 结论
>
> 1. 从数据量来讲，小数据量，插入排序优于快速排序
> 2. 从有序度来讲，高有序度，归并排序优于快速排序



`Arrays`

`TimSort`

`DualPivotQuicksort`

`ArraysParallelSortHelpers`

