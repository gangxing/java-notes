LinkedHashMap

基于双向队列实现，可以用作LRU 缓存的实现方案

可以基于插入顺序迭代，也可以基于访问顺序迭代

```java
//true for access order, false for insertion order, default false
final boolean accessOrder;
```

从代码实现方面讲，`LinkedHashMap extends HashMap`,但底层的实现方案不一样，为什么要继承之。

