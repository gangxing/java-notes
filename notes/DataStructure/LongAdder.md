`LongAdder`

等效于`AtomicLong`,在低并发环境下，两者的特性基本一致，但是在高并发下，`LongAdder`性能更高，是因为需要更多的空间，典型的空间换时间策略。

有两个变量

`base`

`cells`

首先尝试cas更新base值，如果失败则说明有并发，然后当前线程跟特定的cell,然后累加进去。最后获取真正的总数通过累加各cell和base得到。

用到的技术

* CAS

* AtomicInteger

* ThreadLocalRandom

  



问题：

1. ConcurrentHashMap中记录key-value数量时也用到了这个思路，为什么不直接用LongAdder。
2. LongAdder里用到了AtomicInteger,为什么没有IntegerAdder?

