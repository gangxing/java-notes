一、Bean初始化

`BeanFactory.getBean()`

一个Bean引用的依赖是xxService,这是接口，Spring是怎么确定其实现类的呢？

`ProxyFactory.createAopProxy`中确定目标Bean以何种方式(JDK?CGLIB?)

所有XXService都是以XXServiceImpl来创建的，结果就是都是CGLIB？

https://www.cnblogs.com/ronli/p/11437560.html

明天再看。。。。。



自动注入逻辑







看了半天还是没看懂，从IOC容器初始化开始

IOC容器：

```
DefaultListableBeanFactory

DefaultSingletonBeanRegistry

找到所有@Configuration,初始化所有@Bean
@ComponentScan，

堆栈
o.s.core.io.support.PathMatchingResourcePatternResolver.doRetrieveMatchingFiles(PathMatchingResourcePatternResolver.java:782)
 o.s.core.io.support.PathMatchingResourcePatternResolver.retrieveMatchingFiles(PathMatchingResourcePatternResolver.java:768)
 o.s.core.io.support.PathMatchingResourcePatternResolver.doFindMatchingFileSystemResources(PathMatchingResourcePatternResolver.java:723)
 o.s.core.io.support.PathMatchingResourcePatternResolver.doFindPathMatchingFileResources(PathMatchingResourcePatternResolver.java:706)
 o.s.web.context.support.ServletContextResourcePatternResolver.doFindPathMatchingFileResources(ServletContextResourcePatternResolver.java:92)
 o.s.core.io.support.PathMatchingResourcePatternResolver.findPathMatchingResources(PathMatchingResourcePatternResolver.java:510)
 o.s.core.io.support.PathMatchingResourcePatternResolver.getResources(PathMatchingResourcePatternResolver.java:282)
 o.s.context.support.AbstractApplicationContext.getResources(AbstractApplicationContext.java:1309)
 o.s.context.support.GenericApplicationContext.getResources(GenericApplicationContext.java:233)
 o.s.context.annotation.ClassPathScanningCandidateComponentProvider.scanCandidateComponents(ClassPathScanningCandidateComponentProvider.java:421)
 o.s.context.annotation.ClassPathScanningCandidateComponentProvider.findCandidateComponents(ClassPathScanningCandidateComponentProvider.java:316)
 o.s.context.annotation.ClassPathBeanDefinitionScanner.doScan(ClassPathBeanDefinitionScanner.java:275)
 o.s.context.annotation.ComponentScanAnnotationParser.parse(ComponentScanAnnotationParser.java:132)
 o.s.context.annotation.ConfigurationClassParser.doProcessConfigurationClass(ConfigurationClassParser.java:288)
 o.s.context.annotation.ConfigurationClassParser.processConfigurationClass(ConfigurationClassParser.java:245)
 o.s.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:202)
 o.s.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:170)
 o.s.context.annotation.ConfigurationClassPostProcessor.processConfigBeanDefinitions(ConfigurationClassPostProcessor.java:316)
 o.s.context.annotation.ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry(ConfigurationClassPostProcessor.java:233)
 o.s.context.support.PostProcessorRegistrationDelegate.invokeBeanDefinitionRegistryPostProcessors(PostProcessorRegistrationDelegate.java:273)
 o.s.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:93)
 o.s.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:694)
 o.s.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:532)
 o.s.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:140)
 o.s.boot.SpringApplication.refresh(SpringApplication.java:759)
 o.s.boot.SpringApplication.refreshContext(SpringApplication.java:395)
 o.s.boot.SpringApplication.run(SpringApplication.java:327)
 com.hem.xhlapi.Application.main(Application.java:155)]
 
AbstractApplicationContext.refresh 

```

**invokeBeanFactoryPostProcessors**

获取项目中所有Component,@Component,@Service,@Controller

ClassPathScanningCandidateComponentProvider.scanCandidateComponents`

> 具体扫描根目录下的所有class文件如下
>
> `ClassPathBeanDefinitionScanner.doScan(basePackages)`
>
> 递归获取basePackages下的类
>
> `PathMatchingResourcePatternResolver.doRetrieveMatchingFiles()`
>
> 通过上面获取项目中所有class文件，并转换成Resource

这样的话所有接口就被过滤了？？



**onRefresh**



初始化所有业务Bean

**finishBeanFactoryInitialization**

> `DefaultListableBeanFactory.preInstantiateSingletons`
>
> 









二、销毁











