`DispatchServlet.doService`

1. 获取handler（Contrller）,由RequestMappingHandlerMapping

问题：

xxController中调用xxService(有接口)，为什么还是用的`CglibAopProxy`而不是`JdkDynamicAopProxy`

`DefaultAopProxyFactory.createAopProxy(AdvisedSupport config)`中决定采用何种AOP

还得看Bean的初始化流程

