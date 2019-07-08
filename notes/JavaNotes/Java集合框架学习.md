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





<code>ArrayList</code>实现原理，扩容机制，为什么不能在遍历的时候删除元素，即为什么要用迭代器。迭代器实现原理。



**树** （平衡二叉树，B树，B+树，红黑树）， 堆 —>八大排序算法



学习完	

```java
public Vector(int initialCapacity, int capacityIncrement) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }


```



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







