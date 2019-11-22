Heap Sort

以升序举例，将无序的序列构建成一个大顶堆(Heap)，将堆顶元素(最大值)与序列中最后一个元素交换，这为一轮，能确定最大的元素。再对剩下的n-1个元素重复上述过程，确定第二大元素，直至剩下的元素只有一个，即需要n-1轮？

逻辑：

1.构建堆（初始化堆）

2.将堆的根节点与最后一个节点兑换

3.将剩下的节点调整成堆，将根节点按照大顶堆性质往下沉至正确的位置。

4.重复1-3步骤，直至堆只有一个节点

构建堆的逻辑：

可以参照3的思路，从最后一个父节点开始到根节点，将所有父节点下沉至正确的位置

将父节点往下沉的实现

```java
/**
 * 在指定范围内，将父节点按照大顶堆的性质往下沉:父节点要大于子节点，如果父节点小于子节点，下沉之
 *
 * @param arr 源数组
 * @param end 调整的范围，最后一个节点的下标
 * @param pi  父节点下标
 */
private void siftDown(int[] arr, int end, int pi) {
    int ci = (pi << 1) + 1;
    if (ci > end) {//左节点已超出范围，说明当前节点已经没有子节点了
        return;
    }
    //如果右节点存在并且比左节点大，右节点当做子节点代表
    if (ci + 1 <= end && arr[ci + 1] > arr[ci]) {
        ci += 1;
    }

    if (arr[pi] < arr[ci]) {
        swap(pi, ci, arr);
        //原来的父节点落到ci子节点上，继续将ci当做新的父节点往下沉
        siftDown(arr, end, ci);
    }
  /*
   * 如果父节点比两个子节点都大，则下沉动作结束，万一比孙子节点要小呢，这里直接结束貌似逻辑不严谨
   * 其实不然，这个下沉操作在两种场景下调用的，第一次是在构建堆的时候，是从最后一层父节点开始下沉，一
   * 直往上直到根节点。所以每一层父节点为根节点的子树都满足大顶堆堆的性质(子节点小于父节点)。
   * 第二次，才是从根节点开始，当前父节点往下沉，每次遇到的子节点已经是最大的了，所以父节点如果比子节点都
   * 大，一定比后面的子孙节点都大，所以可以不再往下比较了
   */
}


private void swap(int i, int j, int[] arr) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}
```



初始化堆的实现

```java
private void buildHeap(int[] arr) {
  /*
   * 关键在于最后一个父节点下标的计算
   * 在堆的学习中知道 parentIndex = (childIndex - 1)/2 ，不管子节点是左节点还是右节点，此公式通用
   * childIndex = arr.length - 1 
   * 计算得 parentIndex = (arr.length - 1 - 1)/2 = arr.length /2 - 1
   */
    for (int pi = (arr.length >> 1) - 1; pi >= 0; pi--) {
        siftDown(arr, arr.length - 1, pi);
    }
}
```



最终的排序实现

```java
public void sort(int[] arr) {
    buildHeap(arr);//初始化堆

    for (int i = arr.length - 1; i > 0; i--) {
        swap(0, i, arr);//兑换根节点和最后一个节点
        //从叶子层到第二层已经是堆结构,只是此时的根节点不是最大的，所以要将根节点往下沉 放置到合理的位置，保持堆的性质
        siftDown(arr, i - 1, 0);
    }
}
```



若果是降序排列，则构建成小顶堆即可。

空间复杂度O(1)

时间复杂度

[计算参照](https://blog.csdn.net/qq_34228570/article/details/80024306)

$\sum_{i=1}^{k-1}2^{i-1}(k-i)=2^k-k-1$

暂时看求不懂 先记住结论好了

O(nlogn)


