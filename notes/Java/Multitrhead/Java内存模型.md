

Java内存模型是一个规范，为了解决并发模式下的可见性、原子性和顺序性问题。Java内存模型规范了JVM如何提供按需禁用缓存和编译优化的方法。具体来说，这些方法包括volatile、synchronized、final三个关键字，以及6项Happens-Before规则。

**volatile**

原语是禁用CPU缓存，即必须从内存中读取和写入变量。禁用缓存不是Java语言特有的，在C语言中也有这个概念。

>在Java中，volatile除了代表禁用CPU缓存



**Hapens Before规则**

前面一个操作的结果对后续操作是可见的。Happens-Before约束了编译器的优化行为。6项Happens-Before规则都是针对可见性的。

**1. 程序的顺序性规则**

**2. volatile规则**

**3. 传递性规则**

**4. 管程中锁的规则**

**5. 线程start规则**

**6. 线程join规则**

顺序性规则，符合单线程里面的思维，程序前面对某个变量的修改一定是对后续操作可见的。

> 语言级语句是按照程序定义的执行，但是一条语句可能需要多条CPU指令完成，这一组CPU指令的执行顺序不一定按照顺序执行
>
> 比如创建一个对象，并赋值给某个变量，比如`Object a=new Object();`我们的理解其实现过程可能是
>
> 1. 分配一块内存M
> 2. 在内存M上初始化Object对象
> 3. 将M的地址赋值给a变量
>
> 但是实际上优化后的执行顺序可能是下面这样的
>
> 1. 分配一块内存M
>
> 2. 将M的地址赋值给a变量
>
> 3. 在内存M上初始化Object对象
>
>    这个例子。。。。。。。

传递性规则，如果A Happens-Before B,并且B Happens-Before C,则A Happens-Before C。

volatile规则，对一个 volatile 变量的写操作， Happens-Before 于后续对这个 volatile变量的读操作。

> volatile 禁用CPU缓存示例
>
> 从CPU缓存讲volatile
>
> https://juejin.im/post/5c6b99e66fb9a049d51a1094
>
> ```java
> public class VolatileTest {
>     int x = 0;
>   //主线程将run改为false后，因为CPU缓存，子线程没有读取到最新值false，还会继续运行
>   //当用volatile修饰run变量，主线程将run改为false后，子线程会立即读到，结束运行
>   //这就是对volatile run变量的写操作，对其他线程后续读操作是可见的。
>     boolean run = true;
> 
>     public static void main(String[] args) {
>         VolatileTest test = new VolatileTest();
> 
>         new Thread(() -> {
>             while (test.run) {
>                 test.x++;
>             }
>             System.err.println("run end");
>         }).start();
> 
>         try {
>             TimeUnit.SECONDS.sleep(1);
>         } catch (InterruptedException e) {
>             e.printStackTrace();
>         }
> 
>         test.run = false;
>         System.err.println("run false");
>     }
> }
> ```
>
> 





> ```java
> 
> ```



管程中锁的规则，对一个锁的解锁Happens-Before于后续对这个锁的加锁。

> 管程，同步的原语，在Java中，代指synchronized。
>
> 这里的后续加锁是指其他线程获取这个锁之前，当前持有这个锁的线程应该先释放掉。这样当前线程所做的更新对后续获得锁的线程是可见的。



线程start规则，主线程 A 启动子线程 B 后，子线程 B 能够看到主线程在启动子线程 B 前的操作。 从另一个角度看，该start操作Happens-Before于线程B的所有操作。

线程join规则，主线程 A 等待子线程 B 完成(主线程 A 通过调用子线程 B 的 join() 方法实现)，当子线程 B 完成后(主线程 A 中 join() 方法返回)，主线程能够看到子线程的操作。 





互斥锁，解决原子性问题。原子性问题的产生原因是由于线程切换（语言级的一个操作未执行完，其他线程被CPU调度开始执行，也用到了一个对前一个线程来说未完成的变量操作结果）。

把一段需要互斥执行的逻辑成为“临界区”



锁的优化

1. 细化粒度

细化粒度可能会带来死锁问题，

死锁定义：一组互相竞争资源的线程因互相等待，导致永久阻塞的现象。

死锁出现的四个条件：

1. 互斥，共享资源X和Y只能被同一个线程占有；
2. 占有且等待，线程T1已经取得共享资源X，在等待共享资源Y的时候，不释放共享资源X；
3. 不可抢占，其他线程不能抢占线程T1占有的资源；
4. 循环等待，线程T1等待线程T2占有的资源，线程T2等待线程T1占有的资源。

在实现中，只要破坏上面四个条件中的任意一个就可避免死锁现象。