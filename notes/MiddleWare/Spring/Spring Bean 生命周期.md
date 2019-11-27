一、Bean初始化

`BeanFactory.getBean()`

一个Bean引用的依赖是xxService,这是接口，Spring是怎么确定其实现类的呢？

`ProxyFactory.createAopProxy`中确定目标Bean以何种方式(JDK?CGLIB?)

所有XXService都是以XXServiceImpl来创建的，结果就是都是CGLIB？

https://www.cnblogs.com/ronli/p/11437560.html

明天再看。。。。。

默认是用cglib，源码见



```java
//ValidationAutoConfiguration.java
  
@Bean
@ConditionalOnMissingBean
public static MethodValidationPostProcessor methodValidationPostProcessor(
      Environment environment, @Lazy Validator validator) {
   MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
   boolean proxyTargetClass = environment
         .getProperty("spring.aop.proxy-target-class", Boolean.class, true);
   processor.setProxyTargetClass(proxyTargetClass);
   processor.setValidator(validator);
   return processor;
}


  //DefaultAopProxyFactory
  
 /** Default {@link AopProxyFactory} implementation, creating either a CGLIB proxy
 * or a JDK dynamic proxy.
 *
 * <p>Creates a CGLIB proxy if one the following is true for a given
 * {@link AdvisedSupport} instance:
 * <ul>
 * <li>the {@code optimize} flag is set
 * <li>the {@code proxyTargetClass} flag is set 默认设置的，所以aop实例会用cglib
 * <li>no proxy interfaces have been specified
 * </ul>
 *
 * <p>In general, specify {@code proxyTargetClass} to enforce a CGLIB proxy,
 * or specify one or more interfaces to use a JDK dynamic proxy.
 */
//在配置文件application.yml中配置spring.aop.proxy-target-class= false
//会尝试用JDK动态代理实现。

```

JDK动态代理和CGLIB代理两者比较

https://juejin.im/entry/5b95be3a6fb9a05d06732ec2





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

看了半天还是没看懂，从IOC容器初始化开始，既然一个bean的创建过程无从得知，先从自动注入逻辑开始

> 花了一个小时，搭建了一个springboot项目 两张表

https://juejin.im/post/5dd79aa1e51d4522ff65a6cd



还是refresh-> finishBeanFactoryInitialization->preInstantiateSingletons -> getBean -> doGetBean -> getObject -> createBean -> doCreateBean 

调用无参构造器实例化对象，

将实例化的对象放入早期集合中（为了解决循环依赖）

populateBean 获取成员变量，并set(自动注入)，默认是按照名称注入？

postProcessProperties







Mybatis和Spring Boot集成，MyBatis的入口类？

MyBatis提供了MapperBeanFactory,解管Mapper(接口的bean初始化流程)

MyBatis自身的初始化工作(读取配置文件，设置DataSource)在什么开始的呢

> BeanFactory 负责生产Bean,同时这个Factory也是一个Bean,所以叫FactoryBean。
>
> 各种FactoryBean(即各种Factory)都是通过配置文件创建的。



循环依赖，A -> B, B -> A。先创建半成品A，再去创建B，然后处理依赖A，将半成品A注入进来。此时B创建完成，则返回注入给A。



核心思想是通过反射，初始化Bean分为两步

1. 利用反射调用Bean的无参构造器实例化

2. 通过反射找出所有被@Autowire标记的成员变量。递归初始化之

   > 成员明明是接口，为什么找到了其实现类？？
   >
   > 是按照名称注入的还是按照类型注入的？

   在DefaultProxyFactory.createProxy中，传入的AdvisedSupport的targetClass是XXServiceImpl，而不是XXService。

   确定在解析成员变量时，是怎么通过接口定位到实现类的。

   AutowiredAnnotationBeanPostProcessor

   找到XXService 成员变量。

   `DefaultListableBeanFactory.resolveDependency`

   `doResolveDependency`，如果获取到多个，则按照某种策略选择，具体是什么策略？

   `findAutowireCandidates`通过成员类型找到可能的xxxImpl(xxs),怎么匹配上的

   `BeanFactoryUtils.beanNamesForTypeIncludingAncestors`

   `predictBeanType`根据已注册的beanName（包括FanServiceImpl）匹配com.learn.webdemo.service.FanService

   看来这里跟名字没有关系，默认逻辑是按照类型注入的呢...

   `addCandidateEntry`

   `getMergedLocalBeanDefinition`

   ```java
   private final Map<String, RootBeanDefinition> mergedBeanDefinitions
   ```

   在mergedBeanDefinitions中存有一份<xxService,RootBeanDefinition>(XXServiceImpl)

   在什么时候设置的？

   `beanDefinitionMap`中取的，那`beanDefinitionMap`中的又是什么时候放入的呢

   `registerBeanDefinition`

   前面扫描整个项目，拿到所有的bean（@Component,@Service等注解的类）

   `AnnotationConfigUtils.attributesFor`产生beanName

   底层通过`AnnotationReadingVisitorUtils.convertClassValues`（ASM字节码操作）读取其接口？

   `AnnotationBeanNameGenerator.buildDefaultBeanName`

   ` ClassUtils.getShortName`//com.learn.webdemo.service.impl.UserServiceImpl

   得到UserServiceImpl

   `Introspector.decapitalize`将大写转换成小写？得到userServiceImpl

   看了一圈还是要回到上面，mergedBeanDefinitions怎么获取到的

   

3. 将成员变量实例通过反射设置给成员变量。

  > https://juejin.im/post/5c84b5285188257c5b477177
  >
  > 默认按照type来获取，如果获取到多个，则再按照名字匹配。如果名没找到或者按照名字匹配到多个都会抛出异常
  
   

当Bean的某个方法开启了@Transactional后，该Bean被Spring通过CGLIB代理（织入事务处理逻辑）

反之，则是原生的实例

为什么没有用到JDK动态代理





整理一个Bean初始化，用到了哪几个map，



   

二、销毁











