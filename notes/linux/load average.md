1.何为平均负载：单位时间内，系统处于可运行状态和不可中断状态的进程数，即平均活跃进程数。

原文定义(通过 man uptime命令查看 )

System load averages is the average number of processes that are either in a runnable or uninterruptable state.  A process in a runnable state is either using the CPU or waiting  to  use the CPU.  A process in uninterruptable state is waiting for some I/O access, eg waiting for disk.  The averages are taken over the three time intervals.

ps.

可运行状态的进程：是指正在使用CPU或者等待CPU的进程

不可中断进程：正处于内核态关键流程中的进程，并且这些流程是不可打断的，比如等待硬件设备的I/O响应,为了保证数据一致，在等待过程中不能被其他进程打断或者中断，这是系统对进程和硬件设备的一种保护机制。



2.怎么查看系统的平均负载 

第一种方式：uptime 得到如下一行信息

 14:04:43 up 22 days,  3:47,  1 user,  load average: 0.04, 0.03, 0.05

后面三个是指前1分钟、5分钟、15分钟三个时间段内系统的平均负载

第二种方式：top 命令 得到的一行信息跟uptime得到的信息一致



3.平均负载评判

根据平均负载的定义（某一时间段内，系统上平均活跃的进程数量）得知，最理想的情况是一个CPU一直运行一个进程，即平均负载等于机器CPU数量。如果大了，代表多个进程争夺CPU资源，如果小了，代表CPU空闲。

查看机器CPU数量方式：

CPU信息存在<code>/proc/cpuinfo</code>文件中，<code>grep 'model name' /proc/cpuinfo | wc -l </code>即可得到CPU数量

但在实际生产中，平均负载高于CPU数量70%(load_avg/num_cpu>70%)时 ，就算是有点过载了 。这个值不是绝对的，要根据每一次的短期趋势和多次的长期趋势来判定当前系统负载的变化趋势。

画外音：在一般的环境中，机器上部署的服务相对比较固定，所以负载应该变化不大。



4.平均负载 VS CPU使用率

CPU使用率概念：单位时间内CPU繁忙情况的统计（自己理解：单位时间内CPU计算次数）

表面上，平均负载高了，貌似CPU计算更繁忙了，所以两个是等价的概念。其实不然，平均负载除了正在使用CPU的进程，还包括等待CPU和等待I/O的进程。如果I/O密集型的进程，此时平均负载很高，但是CPU使用率并不高。如果是CPU计算密集型进程，此时平均负载高，CPU使用率也会高，两者是对等的概念。



5.如果系统平均负载高了 有哪些套路可以应对？

负载高的场景很少碰到 为了学习，自己模拟 先请stress 和 systat两位大哥上场

Stress是一个Linux系统压测工具，在这里用作异常进程模拟平均负载飙高的场景

sysstat包含了常用的Linux性能监控和分析工具，当前场景下会用到其中的mpstat和pidstat

​	mpstat是一个常用的CPU性能分析工具，可以实时查看每个CPU的性能指标、以及所有CPU的平均指标

​	pidstat是一个常用的进程性能分析工具，可以实时查看进程的CPU、内存、I/O以及上下文切换等性能指标



准备环境

​	1.安装stress 

​	<code>sudo yum install -y epel-release</code>

​	<code>sudo yum install -y stress</code>



场景一、CPU密集型进程

第一个终端，用stress模拟一个CPU使用率100%的进程

<code>$ stress --cpu 1 --timeout 600</code>

1 -CPU使用率100%

600-600秒后超时

第二个终端，用uptime实时查看平均负载

<code>watch -d uptime</code>

-d -表示高亮变化的数据

第三个终端，用mpstat实时查看CPU使用率

<code>mpstat -P ALL 5 1</code>

5 表示间隔5秒后输出一组数据

随着压力测试进行 可以看到平均负载慢慢升高了  通过mpstat看到CPU使用率达到50%(因为有两个CPU ) ，而io基本没变 这是典型的CPU密集型场景 出现这种现象可能代表程序中出现了死循环等bug





