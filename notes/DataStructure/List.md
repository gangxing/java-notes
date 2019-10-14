`List`

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

遍历可以指定从哪个下标开始遍历

`LinkedList`实现了`List`和`Deque`接口，可以同时作为顺序容器，也可以作为队列`Queue`来使用。但是跟`LinkedList`相比，`ArrayDeque`有更好的性能，所以`Stack`,`Queue`特性的数据结构首选是`ArrayDeque`,一探究竟其实现原理，以及为什么更高效。

底层采用数据实现，并持有head和tail两个下标。当数组满后，容量扩大一倍。

数组的长度一定是$$2^n$$($$n>=3$$)

添加元素，head从数组末尾向左移，遇到tial则扩容

(head-1) & (length-1)

删除元素，head向右移

(head+1) & (length-1)

<code>HashSet</code>利用<code>HashMap</code>来实现

研究<code>Arrays</code>和<code>Collections</code>两个工具类，主要学习里面的排序实现