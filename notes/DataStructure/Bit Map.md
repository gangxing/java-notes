Bit Map

[参照](https://blog.csdn.net/u011957758/article/details/74783347)

是个啥 

可以干什么

统计一个数字二进制表现形式中的1的个数,[参照](https://www.cnblogs.com/graphics/archive/2010/06/21/1752421.html)

在redis中的使用



redis中bit map的实现方式



花了一下午 终于实现了 setBit getBit countBit等功能 

一般适用场景 记录用户多个业务开关状态

两种方式

用户维度 记录所有业务类型的开关值，每种开关定义好偏移量

业务类型维度 记录所有用户这个开关的值 用户ID作为偏移量

1一个用户需要空间

100000000/8 byte = 12M 

注意事项

