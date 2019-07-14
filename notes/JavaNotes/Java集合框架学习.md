##                                     Java集合框架学习



**目标**

1.了解JCF(Java Collections Framework)提供的容器、API及其各自的适用场景

2.了解背后的实现原理



**资源**

1.[博客](https://github.com/CarpenterLee/JCFInternals)

2.JDK源码



**方式**

先看笔记，理解核心思想。最后做一个简易的实现Myxxxxx



**检验**

尝试回答网上的面试题



**周期**

一个月 

2019-07-11 光树怕是一个月都学不完额。。。。





<code>ArrayList</code>使用（支持的操作），实现原理，扩容机制，为什么不能在遍历的时候删除元素，即为什么要用迭代器。迭代器实现原理。

整理20190708晚的学习笔记，在java8中，可以在foreach遍历元素时执行操作删除，编译器自动转换成了Iterator方式。主要整理原理和验证思路及工具使用。

底层采用Object[]存数据，如果指定了初始长度，则用初始长度初始化数组，否则未空数组。在每次add时校验数组长度是否足够，判定逻辑：元素的个数+1和数组的长度比较。如果需要扩容，创建一个新的数组，复制元素，并更新原有数组的引用。新数组长度为原数组长度的1.5倍。 

删除元素：删除目标元素后，右边的元素整体左移一位，补缺空位。

空间复杂度O(n)

时间复杂度O(),整体来讲，ArrayList的实现还是比较简单

迭代器：记录游标

modCount:size改变的次数，用于迭代器创建到边历过程中，检查size是否被改变了。如果是，则在遍历时抛出异常，不然没办法遍历。

在java8中，可以在foreach遍历元素时执行操作删除，编译器自动转换成了Iterator方式？

因为两种实现方式的指令一致



<code>LinkedList</code>

双向链表，每个节点持有前一个节点和后一个节点的引用。没有容量的概念。

<code>Node</code>的实现很简单，当前节点实际数据对象，前一个和后一个<code>Node</code>的引用

<code>LinkedList</code>维护<code>first</code>和<code>last</code>两个节点的引用，相当于握住头，便于遍历

<code>HashSet</code>利用<code>HashMap</code>来实现

研究<code>Arrays</code>和<code>Collections</code>两个工具类，主要学习里面的排序实现

<code>**Map**</code>

<code>HashMap</code>

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





<code>LinkedHashMap</code>



<code>TreeMap</code>





<code>com.alibaba.dubbo.common.utils.LRUCache</code>利用LinkedHashMap实现







**树** （平衡二叉树，B树，B+树，红黑树）， 堆 —>八大排序算法

为什么要了解树这种数据结构，因为在<code>HashMap</code>中，如果链表的长度大于8，出于性能的考虑，转换成红黑树实现，但是为什么，不知道。另外一个常见的场景，数据库的索引采用的是B+树实现的，加快文件的访问，为什么也不知道。所以从零较全面地了解下树

学习来源https://juejin.im/post/5ad56de7f265da2391489be3，此篇文章讲解了二叉搜索树(BinarySearchTree)的实现,可以作为树学习的入门篇章

找到一个树的动画神器https://www.cs.usfca.edu/~galles/visualization/Algorithms.html

所谓二叉搜索树，其性质

二叉，每个节点最多只有2个节点，左节点和右节点

有序，每个节点的值大于其左节点的值，小于其右节点的值

按照中序遍历，可得一个递增数列。二叉查找树的查找可以实现类似于有序数列的二分查找，前提是树比较平衡（对称），即每个节点左子树和右子树的节点数相当。二叉查找树的对称性跟插入顺序有关，最坏的情况是按递增或递减序列插入，此时二叉树退化成一个链表。

二叉查找树的删除操作，三种情况

删除的节点没有子节点：将其父节点对其的引用置为空即可

删除的节点只有一个节点：将其父节点对其的引用置换成其子节点的引用即可

删除的节点有两个子节点：

二叉搜索树（二叉排序树）按照中序遍历是一个递增数列，如果删除一个节点后也需要满足中序遍历仍然能得到一个递增数列。所以删除后的节点要用其前驱节点或后继节点来占位，即数列中的前一个或后一个节点。

所谓前驱节点，在树中的表述为当前节点的左节点，左子树中最大的节点，即左子树的最右叶子节点

所谓后继节点，右子树中最小的节点，即右子树的最左叶子节点。

那具体用哪一种都一样

**迭代操作**

按照中序迭代，即递增返回节点。`hasNext()`和`next()`的平均时间复杂度为O(1),如果是这样，肯定需要额外的存储空间，[参照](https://zhuanlan.zhihu.com/p/27679447)

```
维护一个栈，从根节点开始，每次迭代地将根节点的左孩子压入栈，直到左孩子为空为止。

调用next()方法时，弹出栈顶，如果被弹出的元素拥有右孩子，则以右孩子为根，将其左孩子迭代压栈。
```

栈可以用`ArrayDeque` ,暂且不关心其实现原理。 FILO

实现还是蛮简单的，所需要的空间复杂度为O(h),h为树的高度

初始化迭代器

```java
public TreeIterator() {
    stack = new ArrayDeque<>();
    //从根节点开始，所有左节点组成的路径一次压入栈
    Node<V> parent = root;
    pushAll(parent);
}
```



将一个节点开始的左节点路径上的所有节点压入栈，

```java
private void pushAll(Node<V> parent) {
    while (parent != null) {
        stack.push(parent);
        parent = parent.left;
    }
}
```



实现`hashNext()`,只要栈不为空，并且下一个就是置于栈顶的节点

实现`next()`,取出栈顶节点后，如果当前节点有右节点，则以右节点为根节点，将其左节点链路上的所有节点压入栈，这里有点递归的味道，实现逻辑如下

```java
public Integer next() {
    Node<V> cur = stack.poll();
    //以cur为根节点，找出下一个
    if (cur == null) {
        throw new NoSuchElementException("已经没有啦，为什么还要来取");
    }
    //更新next
    if (cur.right != null) {
        pushAll(cur.right);
    }
    return cur.key;
}
```

至此，搜索二叉树的常规操作都实现了，虽然每种操作都还有更优秀的实现方案，比如迭代可以不用栈作为额外的辅助空间，给叶子节点添加前驱和后继两个节点的引用。不过这些都有点类似于茴香豆的茴有几种写法的味道，暂且不表。



搜索二叉树便于搜索的前提是树的平衡性足够好，才能达到类似于二分查找法，每次遍历到一个节点都能过滤掉另一半的节点。所以能不能做到，在保证树遍历的递增前提下，在每次添加或删除节点后，尽可能维持树的平衡。接下来看看所谓的平衡二叉树是不是为了解决这个问题的，而后再看它又是怎么解决的



**平衡二叉树**

Self-balancing binary search tree

何为平衡，通俗来讲，任何一个节点的左右子树的节点数尽肯能相等。更准确的描述：

<font color="#dd0000">**任何一个节点的左右子树的高度差的绝对值不超过1**</font>

[学习来源](https://www.cnblogs.com/suimeng/p/4560056.html)

要维护平衡性，第一个要解决的问题就是怎么快速判定是否满足平衡性，

第二个问题，如果判断不平衡了，怎么修正

越看越复杂，重新找了[一篇](https://www.cnblogs.com/huangxincheng/archive/2012/07/22/2603956.html),感觉讲得还不错

因为每次新增节点或删除节点，都会校验树是否失衡并且修正之。所以，每次改变树结构后，最多只会出现一个失衡节点。因此，改变树结构后，第一步找到失衡节点，如果有，只会出现下面四种情况

1.LL型

![](https://pic002.cnblogs.com/images/2012/214741/2012072218213884.png)

节点的左子树的左节点致使该节点成了失衡节点

2.RR型

![](https://pic002.cnblogs.com/images/2012/214741/2012072218444051.png)

节点的右子树的右节点致使该节点成了失衡节点

3.LR型

![](https://pic002.cnblogs.com/images/2012/214741/2012072219144367.png)

节点的左子树的右节点致使该节点成了失衡节点

4.RL型

![](https://pic002.cnblogs.com/images/2012/214741/2012072219540371.png)

节点的右子树的左节点致使该节点成了失衡节点

现在要解决的问题

1.确认失衡节点并判断其失衡类型

2.修正

每个节点维护一个字段`height`,代表这个节点所处的高度，即以这个节点为根节点的子树的高度。

问题：如果树增加或减少了高度，所有非叶子节点都需要更新这个值？？？？成本也太高了吧。。。

这种方案感觉也不太好，再重新找找，[这篇](https://segmentfault.com/a/1190000006123188)还不错，也是java实现

AVL树节点新增的属性

2.parent-父节点 

树(子树)的高度计算：从树(子树)根节点开始，递归查下去累计计数得到。如果根节点不存在，即一棵空树的高度是-1，实现如下

```java
private int height(Node n) {
    if (n == null)
        return -1;
    return 1 + Math.max(height(n.left), height(n.right));
}
```

先按照二叉搜索树的插入操作增加新的节点，然后找到离新增节点最近的失衡节点（需要向上找，所以需要持有父节点的引用？）。balance==2或-2时这个节点即为失衡节点，平衡之，然后递归向上再平衡之，直至不再有失衡点

又搞了一下午，终于把平衡二叉搜索树的添加节点操作实现了

四种基本类型

LL型，右旋修正

![](https://cdn.sinaimg.cn.52ecy.cn/large/005BYqpgly1g4zfz9zl2wj30b504s0st.jpg)



RR型，左旋修正

![](https://cdn.sinaimg.cn.52ecy.cn/large/005BYqpgly1g4zg6z2gkoj30c904wmx9.jpg)

RL型，先对子树进行右旋，再对包括失衡节点的子树进行左旋



![](https://cdn.sinaimg.cn.52ecy.cn/large/005BYqpgly1g4zgzrr5o9j30fl03qweo.jpg)

当新增一个节点时，如果没有兄弟节点，才有可能产生失衡节点。

搞了一天，对左旋和右旋有点似懂非懂

在二叉树中，以左中右三个节点为一个单位，将这个三角形想象成一个管道，所谓左旋，将三个或两个节点逆时针向前移动一位。因为这三个节点满足递增或递减顺序，所以，当移动三个点时，要满足LL或RR型，否则先将后面两个节点拉直，然后再左旋或右旋。











**红黑树**



**B树**



**B+树**







学习完	





```java
public static void main(String[] args){
  System.out.println("Hello World!");
}
```




```java
public Vector(int initalCapacity, int capacityIncrement) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }
```





