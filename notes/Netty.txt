一、先了解下网络IO模型
https://www.cnblogs.com/linganxiong/p/5583415.html

1.blocking io
application 调用recvfrom转入kernel 两个过程 wait for data,copy data from kernel to user。这个过程是阻塞的

2.nonblocking io


线程模型