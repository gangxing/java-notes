LinkedHashMap

保证了迭代顺序

p.s. `HashMap`不能保证迭代顺序，是因为扩容后会rehash，重组节点间的相对位置，所以不能保证迭代顺序

基于双向队列实现，可以用作LRU 缓存的实现方案

可以基于插入顺序迭代，也可以基于访问顺序迭代

```java
//true for access order, false for insertion order, default false
final boolean accessOrder;
```

从代码实现方面讲，`LinkedHashMap extends HashMap`,但底层的实现方案不一样，为什么要继承之。

Node 新增了`before`和`after`前后两个节点的引用，在创建新节点时维护这两个引用。最后遍历时，相当于`LinkedList`从头通过`after`一直遍历到最后一个。除此之外，其他逻辑全部复用的`HashMap`。

所谓的基于访问顺序，是要在构造`LinkedHashMap`时通过设置`accessOrder`来确定的，中间不可变更。

如果是基于访问顺序，在每次`get`操作后，将访问到的节点放到双向链表尾部`tail`。

思考

`LinkedHashMap`