HashMap

采用数组+链表（Java8中当链表长度大于8时改为红黑树，小于8时退回为链表。）

capacity size 加载因子 扩容机制 hash算法 为什么容量一定是2次方，容量和数组

Node实现了Map.Entry每个键值对，

hash-key的hash值，***

key-key

value

next -为什么要保存下一个键值对的引用？？？用于当hash冲突时，持有当前桶（bin）链表的后一个Node

threshold-The next size value at which to resize (capacity * load factor).

loadFactor

是怎么保证key不重复的 ？？？？ 扩容机制？

Put操作

因为相同的key计算出的hash是相同的，进一步计算出数组的下标也是相同的，然后先根据这个下标取出已有的桶,如果桶里有一个Node，并且两者的key相同，则替换value。如果是TreeNode(extends Node),则插入到树中（具体逻辑因为跟<code>LinkedHashMap</code>关联，所以待研究）。最后一种情况，遍历链表，每个节点都持有后一个节点的引用。如果只有一个节点，则创建新的节点加入到这个节点的后面。如果还有后续节点，则先比较目标节点的key值是否相同，即是否是替换value操作。直到遍历到链尾。最后判断是否需要树化。

关键点 扩容逻辑和链表与树之间的转化逻辑。



Get操作

计算key的hash值，确定桶，检查第一个节点，如果刚好相等就直接返回。如果是树，按照树的方式查找（待研究），如果是链表，则遍历之

为什么一定先判断桶的第一个节点，花了很大的力气，尽可能让每个桶只有单个节点。所以大部分的桶就只有这一个节点。遇到小部分不是的情况下。才会根据节点的类型来遍历。



Remove操作

跟Get操作流程类似，找到后目标节点后，用其next节点替代



遍历（不保证遍历顺序）

直接返回entrySet,按照个人理解，在每次增删改Node时，就同步更新维护entrySet。或者从table的第一个元素开始，每遍历到一个同的firstNode时，一直遍历它的next直到next为null，然后回来遍历table的下一个元素直至遍历完最后一个元素（桶）

问题：

1.既然JDK选择缓存一个entrySet，但没看到entrySet的更新逻辑。

2.Set实现的方式是采用Map的key不重复来实现的。然而HashMap的遍历实现逻辑又依赖Set，一时间理解不到

现在网上找找别人的梳理

http://www.mamicode.com/info-detail-2034228.html

https://blog.csdn.net/zzy7075/article/details/53067618

这两篇文章讲得意思差不多（肯定差不多，因为都是对同一个东西的讲解，如果两个人讲得不一样，至少有一个人是错的，哈哈哈哈）

其实我们每次都是使用的for增强循环，但其本质（编译器还原后）是通过entrySet的迭代器实现的。所以了解entry的迭代器实现就完了，所以，显示用entrySet的iterator的debug源码

通常的使用方式

```java
Set<Map.Entry<Integer,String>> entrySet=map.entrySet();
for (Map.Entry<Integer,String> entry:entrySet){
    System.err.println(entry.toString());
}
```

编译器还原后的方式(这么写，IDE会提示因为循环内部没有改变iterator,所以建议用上面的方式遍历，其实本质都是一回事，无需纠结这些细节)

```java
Set<Map.Entry<Integer,String>> entrySet=map.entrySet();
Iterator<Map.Entry<Integer,String>> iterator=entrySet.iterator();
while (iterator.hasNext()){
    Map.Entry<Integer,String> nextEntry=iterator.next();
    System.err.println(nextEntry.getKey()+"->"+nextEntry.getValue());
}
```

即便是这样，还是不理解，因为entrySet毕竟是一个变量，有引用（内存地址），那这个变量在什么时候被赋值的？？？？？先不管看iterator()和next()实现

猜测其entrySet其实在前面哪一步就已经被调用了，所以entrySet被赋值了。

<code>entrySet</code>的<code>Set</code>具体实现类是<code>EntrySet</code>,<code>EntrySet</code>的iterator返回的是<code>EntryIterator</code>,但它又是<code>HashIterator</code>,并且并没有重写或增加什么功能，搞不懂为什么不直接用<code>HashIterator</code>。所以现在的重点到了<code>HashIterator</code>的构造和<code>nextNode()</code>的实现逻辑

<code>HashIterator</code>维护的变量

```java
Node<K,V> next;        // next entry to return
Node<K,V> current;     // current entry
int expectedModCount;  // for fast-fail
int index;             // current slot
```



初始化（构造器）

```java
HashIterator() {
    expectedModCount = modCount;
    Node<K,V>[] t = table;
    current = next = null;
    index = 0;
    if (t != null && size > 0) { // advance to first entry
        do {} while (index < t.length && (next = t[index++]) == null);
    }
}
```

主要找出首个元素，即遍历数组，很简单，不用多说。跟个人理解一致，为了实现遍历，所以要记录当前的

遍历下标和下一个节点。万变不离其宗。

<code>nextNode()</code>实现

```java
final Node<K,V> nextNode() {
    Node<K,V>[] t;
    Node<K,V> e = next;//先保存要返回的节点，后面再更新next，供下次再来取
    if (modCount != expectedModCount)
        throw new ConcurrentModificationException();
    if (e == null)
        throw new NoSuchElementException();
  //如果新的next不为空，则就是下一次要取的(当前要返回的Node在链表上)
  //如果新的next为空，如果table不为空，尝试去看下一个桶上是否还有节点，如果有更新index和next
  /*
   * 1.吐槽一下JDK源码，在查询的时候又在更新，并且这种套路很常见，不易于阅读
   * 2.这种遍历方式跟前面我猜测的是一致的。当然了，这本来就是理所应当的遍历方式
   */
    if ((next = (current = e).next) == null && (t = table) != null) {
        do {} while (index < t.length && (next = t[index++]) == null);
    }
    return e;
}
```

至此，<code>Map</code>之遍历的实现方式就清晰了。除了上面<code>entrySet</code>这个变量在何时赋值的不理解外。

2019-07-18，找了半天终于找到了一个[靠谱的解释](https://stackoverflow.com/questions/41714233/how-and-when-hashmap-initialize-entryset-and-add-value-into-it)

It is your debugger that fools you. The debugger view calls `toString()` which in fact calls `entrySet()` (see `AbstractMap.toString()`). That is why the `entrySet` was already initialized, when you looked at it.

If you look in there via reflection utils, e.g. with the following code:

```java
HashMap<String, String> map = new HashMap<>();

Field entrySetField = HashMap.class.getDeclaredField("entrySet");
entrySetField.setAccessible(true);
Object entrySet = entrySetField.get(map);
System.out.println("entrySet = " + entrySet);
System.out.println("map.toString() = " + map.toString());
entrySet = entrySetField.get(map);
System.out.println("entrySet = " + entrySet);
```

常见的问题

为什么遍历Map时，优先使用<code>entrySet</code>,因为这种遍历方式是最直接的，符合Map内部的存储结构的

为什么HaspMap不保证迭代的顺序

首先，何为顺序，要么是key之间有大小关系，见<code>TreeMap</code>。要么是按照插入的顺序，见<code>LinkedHashMap</code>

因为<code>HashMap</code>每个Node在遍历中出现的次序受key的hash值影响（决定在数组的哪个桶上）和插入的次序（如果两个key的hash值一样，则先插入的先于后插入的出现，不清楚在红黑树中是否满足这样的说法）



当增加key value mapping（Node）时，为了解决先天的hash冲突问题，有两种方案，一是resize，即扩容（不一定能完彻底解决）。二是将多个mapping放在一个桶内，以链表或树的结构维护这些数据。具体选择哪种方案，权衡后选择。追求的目标：每个桶只放一个mapping。

问题：如果当前当前桶里已经有

怎么走hash冲突逻辑



确定bin（数组元素）下标计算公式 i=(n - 1) & hash ,跟常规的取余不一样，怎么保证结果值一定是合法的下标？结果一定小于等于n-1

n -数组的长度，即bin的数量

hash -key的hash值 <code>HashMap</code>中：hash= key.hashCode() ^( key.hashCode>>>16)

