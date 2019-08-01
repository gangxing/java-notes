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

2019-07-30 不知不觉又中断了10来天，每天都坚持好难啊。。。

每次新增mapping时，先查找，如果找到了相同的key（根据hashCode和equals），则替换value。

扩容机制

判断条件：size> capacity * loadFactor  capacity-数组的长度 size-节点数量（键值对）

扩容程度：数组长度扩大到原来的2倍

按照个人理解：新建一个空数组，遍历原数组（遍历到每个节点），按照key的hashCode值重新插入到新数组中。实际方案见`resize()`，看是否跟个人理解的方案一致

扩容后节点的重新放置，经过查看源码，发现桶内只有一个节点的时候，确实是放到新的桶内，当桶内是链表或者红黑树的时候是另外的逻辑

```java
for (int j = 0; j < oldCap; ++j) {
    Node<K,V> e;
    if ((e = oldTab[j]) != null) {
        oldTab[j] = null;
        if (e.next == null)//只有一个节点
            newTab[e.hash & (newCap - 1)] = e;
        else if (e instanceof TreeNode)//桶内是红黑树节点
            ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
        else { // preserve order 桶内是链表节点
            Node<K,V> loHead = null, loTail = null;
            Node<K,V> hiHead = null, hiTail = null;
            Node<K,V> next;
            do {
                next = e.next;
                if ((e.hash & oldCap) == 0) {
                    if (loTail == null)
                        loHead = e;
                    else
                        loTail.next = e;
                    loTail = e;
                }
                else {
                    if (hiTail == null)
                        hiHead = e;
                    else
                        hiTail.next = e;
                    hiTail = e;
                }
            } while ((e = next) != null);
            if (loTail != null) {
                loTail.next = null;
                newTab[j] = loHead;
            }
            if (hiTail != null) {
                hiTail.next = null;
                newTab[j + oldCap] = hiHead;
            }
        }
    }
}
```

为了测试hash碰撞，先要了解`HashMap`中的hash值计算逻辑

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

依赖key的`hashCode`计算方式，如果是数字，则为本身,字符串的计算逻辑

```java
public int hashCode() {
    int h = hash;
    if (h == 0 && value.length > 0) {
        char val[] = value;

        for (int i = 0; i < value.length; i++) {
            h = 31 * h + val[i];
        }
        hash = h;
    }
    return h;
}
```

找出hash碰撞的字符串，见[这篇文章](https://donlianli.iteye.com/blog/1979674)

通过随机找到一组hash碰撞的字符串

```java
[l`s, lb5, mAs, laT, n"s, mBT, n#T, mC5, n$5]
```

这9个key可以始桶从空到链表再到红黑树的转化逻辑都走到，继续研究put操作，当时链表时直接追加到后面，当链表长度 `binCount`达到8的时候,将链表树化之，问题，如果这时候刚好触发了扩容操作呢，先树化，再扩容，树化的判断逻辑

```java
static final int TREEIFY_THRESHOLD = 8;

for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st 先比较 再加？
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
```



研究到这里有个误区，其实多个节点在同一个桶内，不一定要hahsCode相同，只要`hashCode & (capacity-1)`计算出的下标一致就行，即在通数量部分的低位一样就行。例如

同数量是4，其二进制是100,capacity-1的二进制是11,在这种情况下，只要key的hashCode低两位是一致就会落到同一个桶内，比如低两位是11，满足的hashCode 有 11(3), 111(7), 1111(15),11111(31),111111(63), 1111111(127), 11111111(255),111111111(511), 1111111111(1023)。。。。

假设初始化容量为3，规整成2的n次方后为4，在放入的过程中，会先经历扩容，桶内形成链表，扩容，一个链表分为高低两个链表，落入高低链表的判断依据`hash & oldCapaticy==0`低链表依旧在原来的下标上，高链表下标增加原数组长度，链表树化，在树化前会判断容量是否达到了树化的最低容量`MIN_TREEIFY_CAPACITY=64`，如果没达到，先扩容，如果达到了，真正树化，

```java
treeifyBin
```

先将次链表转换成TreeNode为节点的双向链表(每个节点持有前一个和后一个节点的引用)，

遍历双向列表，构造一颗红黑树，构造完之后，需要确保数组的对应位置的桶的第一个节点是树的根节点，因为遍历的时候只能从根节点遍历。按照我的的理解，直接一句`tab[index]=root`就可以了，但是源码中还有一大堆逻辑，后续再研究

```java
static <K,V> void moveRootToFront(Node<K,V>[] tab, TreeNode<K,V> root) {
    int n;
    if (root != null && tab != null && (n = tab.length) > 0) {
        int index = (n - 1) & root.hash;
        TreeNode<K,V> first = (TreeNode<K,V>)tab[index];
        if (root != first) {
            Node<K,V> rn;
            tab[index] = root;//关键的一句，持有根节点 下面在干啥 不求甚解
            TreeNode<K,V> rp = root.prev;
            if ((rn = root.next) != null)
                ((TreeNode<K,V>)rn).prev = rp;
            if (rp != null)
                rp.next = rn;
            if (first != null)
                first.prev = root;
            root.next = first;
            root.prev = null;
        }
        assert checkInvariants(root);
    }
}
```

至此，Put操作大概梳理完毕，Get操作很简单，定位桶，再定位节点（如果桶内是链表或红黑树）

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

此处的Set并不是用了Set的实现比如HashSet又重复存储了一份数据，而是自己实现了Set语义，即一个集合，跟HashSet没有任何关系。

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

因为rehash后，节点相对位置关系变了。所以不能保证迭代顺序。

当增加key value mapping（Node）时，为了解决先天的hash冲突问题，有两种方案，一是resize，即扩容（不一定能完彻底解决）。二是将多个mapping放在一个桶内，以链表或树的结构维护这些数据。具体选择哪种方案，权衡后选择。追求的目标：每个桶只放一个mapping。

问题：如果当前当前桶里已经有

怎么走hash冲突逻辑



确定bin（数组元素）下标计算公式 i=(n - 1) & hash ,跟常规的取余不一样，怎么保证结果值一定是合法的下标？结果一定小于等于n-1

n -数组的长度，即bin的数量

hash -key的hash值 <code>HashMap</code>中：hash= key.hashCode() ^( key.hashCode>>>16)



至此，HashMap的常规操作已经梳理完毕，但是它是非线程安全的。为了保证多线程环境还能用`HashMap`类似的数据结构，或者说加上什么操作，可以保证这种数据结构适用于多线程环境下。

首先得找到症结所在，为什么`HashMap`不适用于多线程环境，具体是哪个操作不支持。

[这篇文章](https://my.oschina.net/tantexian/blog/654260)讲得还不错，但是粗略地看了一遍，还是看不懂

基于jdk8亲测并不会出现并发导致的死循环问题，40个线程put,40个线程get。。。都能正常执行完成，至于数据是否准确，还无从得知。难道是JDK8做了什么优化？？？

第一种方案，



