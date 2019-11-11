Nginx和Tomcat是同一种东西，都是作为web服务器，支持HTTP协议，接收HTTP请求，返回响应

一个Nginx支持部署多个server。Nginx根据端口和server_name二元组定位一个特定的Server,当未找到相应的Server，就返回404。不同的Server可以配置不同的端口。

> 域名只是对IP的映射，相当于IP的别名，跟端口没任何关系，Http协议默认的端口是80。所以xxx.com实际上是xxx.com:80。

客户端发出一个请求，先经过DNS确定IP。根据IP和服务器建立连接，Linux内核根据端口将包丢给Nginx。

Nginx先根据端口找Server,如果有多个Server监听同一个端口，则Nginx再根据请求的域名和配置的server_name再定位Server。此处的server_name可以是当前机器的外网ip，这样就可以不用域名了。如果没有Server的name是ip，则根据IP直接访问是找不到Server的，所以会返回404。

所以用一个Nginx实现多个Server时，所有Server都监听80端口，但server_name配不同的域名，这样就可以实现不同的域名访问不同的server。当然这也可以用端口来区分，但是这样的话，一是会暴露多个端口，加大安全风险，二是客户端要带端口，看起来不优雅？哈哈哈

> 一个Server可以配置多个server_name, 比如a.xxx.com b.xxx.com等等

Nginx还有一个特别经典的应用场景，就是作为反向代理服务器，本质上还是一个Server，只不过这个Server不再自己响应请求（比如返回html文件或任何计算），而根据upstream配置的内部服务器列表，将请求按照一定的负载均衡策略将请求转发给内部的服务。



> 客户端先通过DNS拿到域名对应的IP，再根据IP将数据包发给服务器，那服务器是怎么知道客户端请求的域名的呢？通过http请求头？哪个头？
>
> Host