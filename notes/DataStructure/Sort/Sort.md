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

|        | 时间复杂度 |空间复杂度 |是否基于比较|稳定性 |适用场景 |
| :------ | :--------- |:--------- |:--------- |:--------- |:--------- |
| Bubble Sort | $O(N^2)$ |$O(1)$     | 是 |稳定||
| Insertion Sort | $O(N^2)$ |$O(1)$     |是     |稳定||
| Selection Sort | $O(N^2)$ |O(1)     |是   | 不稳定 ||
| Merging Sort   | $O(NlogN)$ |     |是    | ||
| Quick Sort     | $O(NlogN)$ |     |是     |||
| Shell Sort | 单元格     |单元格     |     |||
| Heap Sort | 单元格     |单元格     |     |||
| Radix Sort | $O(N)$ |单元格     |不是     |||
| Counting Sort | $O(N)$ |单元格     |不是     |||
| Bucket Sort | $O(N)$ |单元格     |不是     |||







`Arrays`针对不同数据源对算法的选择





`Arrays`

`TimSort`

`DualPivotQuicksort`

`ArraysParallelSortHelpers`
