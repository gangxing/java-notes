`ConcurrentHashMap`



HashMap的常规操作已经梳理完毕，基于数组，能实现O(1)时间复杂度的操作，但是它是非线程安全的。为了保证多线程环境还能用`HashMap`类似的数据结构，或者说加上什么操作，可以保证这种数据结构适用于多线程环境下。

首先得找到症结所在，为什么`HashMap`不适用于多线程环境，具体是哪个操作不支持。

HashMap有size,table等共享的成员变量，在插入数据时，存在修改这些共享成员变量的操作，所以不是线程安全的。另外，对于put后立即get操作场景，假如线程1执行完put操作后，刚好让出CPU时间片（或者主动sleep）,这时刚好线程2也put了一个相同的key（value不同），然后线程1获得CPU时间片，这时读到的不再是自己put的值，这也是非线程安全的一种体现。

为了解决并发问题，简单的解决办法就是将所有操作都串行化(每个操作都加锁)，见`Collections.synchronizedMap()`,类似的方案还有`HashTable`,加锁保证了并发操作下的正确性，但是效率也会大大降低。在保证正确性的前提下，有没有更高效的方案呢？我的理解，前面的方案之所以效率低下，是因为只有一把锁，所有线程都必须获得者同一把锁，但是基于HashMap的实现方案（数组+链表/红黑树），只对数组的每个元素（bin）加锁，这样对不同的bin的操作是可以支持并发的，但这只是基于数组长度固定的前提，但是数组是会扩容的，这个变更操作是面向整个数组，并且部分bin中的部分Node会被移动，所以这种方案也是不可行的。因此，并发操作的难点就落到了怎么支持高效并发地扩容。先看`ConcurrentHashMap` 是怎么做到的？其核心思想是

1. 分段

2. CAS

3. synchronized

   关于ConcurrentHashMap的剖析，见下面这篇文章

   [https://yfzhou.coding.me/2018/12/24/%E5%89%91%E6%8C%87ConcurrentHashMap%E3%80%90%E5%9F%BA%E4%BA%8EJDK1-8%E3%80%91/](https://yfzhou.coding.me/2018/12/24/剑指ConcurrentHashMap[基于JDK1-8]/)

   

   ConcurrentHashMap在数据组织方面，和HashMap是一致的，底层都是采用数组+链表/红黑树，

   增加了一些特殊Node，其hash为负数，具体值如下面所示

   ```java
   /*
    * Encodings for Node hash fields. See above for explanation.
    */
   static final int MOVED     = -1; // hash for forwarding nodes
   static final int TREEBIN   = -2; // hash for roots of trees
   static final int RESERVED  = -3; // hash for transient reservations
   static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
   ```



源码注释说明，

当插入一个Node到空bin时，采用CAS方案，对于其他的写操作（比如insert，update，delete）根据不同的bin获取各自的锁（哎，这一点思想不就是前面我自己设想的吗，啧啧啧！），但是这样当bin的数量（数组长度）很大时，会存在多个锁，很浪费空间，因此将每个bin的第一个元素作为锁。

每个bin都有自己的锁这种方案的主要缺点是，并发操作同一个bin里不同的Node(链表/红黑树)是需要获取同一把锁的，因此效率会低下，但是从统计上来说，这不是一个问题，至于为什么，有一大堆分析，暂且不表。

> 负数hash被用作特殊用途，所以用户的key 的hash就不能再有负数了，这点通过spread确保hash不为负数
>
> ```java
> static final int spread(int h) {
>     return (h ^ (h >>> 16)) & HASH_BITS;//HASH_BITS=0X7FFFFFFF
> }
> ```



我的理解，一是缩小锁的范围，比如上面的每一个bin一个锁，另一方向，尽可能减少锁住的逻辑，能不用锁的地方，尽量不用锁。



​	helpTransfer,在扩容的时候，其他线程可以加入进来，”帮忙“扩容，而不是傻傻地等待锁。这个。。。扩容还可以多个线程一起帮忙做？？？所谓的帮忙是什么意思？？？



`ConcurrentHashMap`的key为什么不能为null

除了HashMap中所需要的字段，还增加以下几个

```java 
//用在扩容时
private transient volatile Node<K,V>[] nextTable;

//key-value数量？？？
private transient volatile long baseCount;

/*
 * 数据初始化或扩容控制
 * 负数时表示数组正在被初始化或者扩容，具体地
 * -1表示正在初始化
 * -(1+正在扩容的线程数量)，比如，此时有3个线程都在扩容，则sizeCtl=-4
 * 当为非负数时，表示下一步的数组长度，具体地
 * 当数组为null时，表示初始化的长度，默认是0，如果不为null时，表示扩容后的数组长度
 */

private transient volatile int sizeCtl;

/**
 * 不求甚解
 */
private transient volatile int transferIndex;

/**
 * 不求甚解
 */
private transient volatile int cellsBusy;

/**
 * 不求甚解
 */
private transient volatile CounterCell[] counterCells;


```





一通操作，还是不求甚解，先根据put操作来看看流程

1. 计算hash

2. 判断table是否已初始化，如果没有，则初始化之

3. 定位Node,如果Node为null，将当前key-value构建一个Node,利用CAS赋值，用CAS一般都需要自旋。

4. 如果Node的hashCode == MOVED,说明正在扩容？则帮助扩容？？？怎么个帮助法？？

5. 所谓帮助扩容，是尝试去扩容，那先看看扩容在干什么,辣么长。。。。。

   ```java
   private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
       int n = tab.length, stride;
       if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
           stride = MIN_TRANSFER_STRIDE; // subdivide range
       if (nextTab == null) {            // initiating
           try {
               @SuppressWarnings("unchecked")
               Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];
               nextTab = nt;
           } catch (Throwable ex) {      // try to cope with OOME
               sizeCtl = Integer.MAX_VALUE;
               return;
           }
           nextTable = nextTab;
           transferIndex = n;
       }
       int nextn = nextTab.length;
       ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
       boolean advance = true;
       boolean finishing = false; // to ensure sweep before committing nextTab
       for (int i = 0, bound = 0;;) {
           Node<K,V> f; int fh;
           while (advance) {
               int nextIndex, nextBound;
               if (--i >= bound || finishing)
                   advance = false;
               else if ((nextIndex = transferIndex) <= 0) {
                   i = -1;
                   advance = false;
               }
               else if (U.compareAndSwapInt
                        (this, TRANSFERINDEX, nextIndex,
                         nextBound = (nextIndex > stride ?
                                      nextIndex - stride : 0))) {
                   bound = nextBound;
                   i = nextIndex - 1;
                   advance = false;
               }
           }
           if (i < 0 || i >= n || i + n >= nextn) {
               int sc;
               if (finishing) {
                   nextTable = null;
                   table = nextTab;
                   sizeCtl = (n << 1) - (n >>> 1);
                   return;
               }
               if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                   if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                       return;
                   finishing = advance = true;
                   i = n; // recheck before commit
               }
           }
           else if ((f = tabAt(tab, i)) == null)
               advance = casTabAt(tab, i, null, fwd);
           else if ((fh = f.hash) == MOVED)
               advance = true; // already processed
           else {
               synchronized (f) {
                   if (tabAt(tab, i) == f) {
                       Node<K,V> ln, hn;
                       if (fh >= 0) {
                           int runBit = fh & n;
                           Node<K,V> lastRun = f;
                           for (Node<K,V> p = f.next; p != null; p = p.next) {
                               int b = p.hash & n;
                               if (b != runBit) {
                                   runBit = b;
                                   lastRun = p;
                               }
                           }
                           if (runBit == 0) {
                               ln = lastRun;
                               hn = null;
                           }
                           else {
                               hn = lastRun;
                               ln = null;
                           }
                           for (Node<K,V> p = f; p != lastRun; p = p.next) {
                               int ph = p.hash; K pk = p.key; V pv = p.val;
                               if ((ph & n) == 0)
                                   ln = new Node<K,V>(ph, pk, pv, ln);
                               else
                                   hn = new Node<K,V>(ph, pk, pv, hn);
                           }
                           setTabAt(nextTab, i, ln);
                           setTabAt(nextTab, i + n, hn);
                           setTabAt(tab, i, fwd);
                           advance = true;
                       }
                       else if (f instanceof TreeBin) {
                           TreeBin<K,V> t = (TreeBin<K,V>)f;
                           TreeNode<K,V> lo = null, loTail = null;
                           TreeNode<K,V> hi = null, hiTail = null;
                           int lc = 0, hc = 0;
                           for (Node<K,V> e = t.first; e != null; e = e.next) {
                               int h = e.hash;
                               TreeNode<K,V> p = new TreeNode<K,V>
                                   (h, e.key, e.val, null, null);
                               if ((h & n) == 0) {
                                   if ((p.prev = loTail) == null)
                                       lo = p;
                                   else
                                       loTail.next = p;
                                   loTail = p;
                                   ++lc;
                               }
                               else {
                                   if ((p.prev = hiTail) == null)
                                       hi = p;
                                   else
                                       hiTail.next = p;
                                   hiTail = p;
                                   ++hc;
                               }
                           }
                           ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                               (hc != 0) ? new TreeBin<K,V>(lo) : t;
                           hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                               (lc != 0) ? new TreeBin<K,V>(hi) : t;
                           setTabAt(nextTab, i, ln);
                           setTabAt(nextTab, i + n, hn);
                           setTabAt(tab, i, fwd);
                           advance = true;
                       }
                   }
               }
           }
       }
   }
   ```

是否要扩容，肯定是根据count来判断的，那怎么来存储count的呢？？？看size()的实现

```java
final long sumCount() {
    CounterCell[] as = counterCells; CounterCell a;
    long sum = baseCount;
    if (as != null) {
        for (int i = 0; i < as.length; ++i) {
            if ((a = as[i]) != null)
                sum += a.value;
        }
    }
    return sum;
}
```

可以看出数量存储在两个地方，一个是`baseCount`,一个是`counterCells`，根据文档说明，当不是并发插入时，通过CAS直接累加到`baseCount`中，当有并发时，加到每个CounterCell中？？

再看这两个变量如何被更新的，每次成功插入一个键值对后，都会增加count,具体逻辑见

```java

private final void addCount(long x, int check) {
    CounterCell[] as; long b, s;
    if ((as = counterCells) != null ||
        !U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {
        CounterCell a; long v; int m;
        boolean uncontended = true;
        if (as == null || (m = as.length - 1) < 0 ||
            (a = as[ThreadLocalRandom.getProbe() & m]) == null ||
            !(uncontended =
              U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
            fullAddCount(x, uncontended);
            return;
        }
        if (check <= 1)
            return;
        s = sumCount();
    }
    if (check >= 0) {
        Node<K,V>[] tab, nt; int n, sc;
        while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&
               (n = tab.length) < MAXIMUM_CAPACITY) {
            int rs = resizeStamp(n);
            if (sc < 0) {
                if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                    sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                    transferIndex <= 0)
                    break;
                if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                    transfer(tab, nt);
            }
            else if (U.compareAndSwapInt(this, SIZECTL, sc,
                                         (rs << RESIZE_STAMP_SHIFT) + 2))
                transfer(tab, null);
            s = sumCount();
        }
    }
}
```



我曹，看不下去了，怎么这么复杂。。。。。





如果在插入过程中，发现数组正在扩容，并且参与扩容的线程数量没达到最大时（`MAX_RESIZERS=65535`），则加入到帮忙大军中，帮助扩容，所谓扩容，有两个动作，一是创建2倍长度的新数组，二是搬移Node到新数组中。在这两个操作中，第一个操作只能是由一个线程完成，能够一起完成的是Node的搬运工作。这就是为什么在保证并发正确的前提下，还能高效的关键所在。

> 这一通说下来，其实还是不懂，为什么可以多个线程并发搬运Node,怎么组织的？
>
> 每个线程只分配部分Node的搬运？那怎么计算当前线程需要搬运的Node范围呢？？？？



其实现逻辑如下

```java
final Node<K,V>[] helpTransfer(Node<K,V>[] tab, Node<K,V> f) {
    Node<K,V>[] nextTab; int sc;
    if (tab != null && (f instanceof ForwardingNode) &&
        (nextTab = ((ForwardingNode<K,V>)f).nextTable) != null) {
        int rs = resizeStamp(tab.length);
        while (nextTab == nextTable && table == tab &&
               (sc = sizeCtl) < 0) {
            if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                sc == rs + MAX_RESIZERS || transferIndex <= 0)
                break;
            if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) {
              //真正地扩容实现
                transfer(tab, nextTab);
                break;
            }
        }
        return nextTab;
    }
    return table;
}
```



ForwardingNode`,hash=MOVED,表示在扩容时插入

> 内置的特殊Node，key ,value都为null，hash为负数，
>
> ```java
> 
> //
> ForwardingNode MOVED
> 
>   //
> ReservationNode RESERVED
> 
> TreeNode
> 
> TreeBin //包装TreeNode,作为树的根节点？？？
> ```
>
> 所以ConcurrentHashMap不允许插入Key或Value为null的键值对

插入元素，会有以下几种场景

1. table未初始化，先初始化之，
2. 













