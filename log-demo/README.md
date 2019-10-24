了解slf4j logback-classic
一、使用，包括配置，级别，日志格式、日志文件切割

二、加载时机，当一个项目用到了很多库，库里都有日志使用，日志实现的选择，初始化时机


三、实现架构、源码大概学习

四、自己写一个库，怎么优雅用日志实现


解惑
1.日志系统在什么时候初始化的
>使用`LoggerFactory.getLogger`的时候

2.LoggerFactory只是接口，怎么找日志实现的
>`LoggerFactory`的实现，大概是从类路径上找实现

3.去哪儿找配置文件，默认的配置文件名称，是否支持自定义
`StaticLoggerBinder` 有个问题，这个类是logback-classic的，但是在slf4j-api中引用的。。。。
如果不引入logback-classic，是不是就报错了 是滴
内部调用`ContextInitializer.init`,再执行`findURLOfDefaultConfigurationFile`

支持自定义，System.setProperty("logback.configurationFile","xxx.xml")
如果没有指定配置文件，则去resources下找
第一步，尝试找logback-test.xml
第二步，尝试找logback.groovy
第三步，尝试找logback.xml

如果都没找到，就调用`BasicConfigurator`初始化一个`ConsoleAppender`,默认是debug级别

当然日志的落地有多中appender，包括console，file，rolling file, socket, db
其中console appender底层利用的是System.out实现的
默认的console appender的日志格式可以更改吗？
如果没有自定义配置，则利用`BasicConfigurator`，其中Layout也是固定的

如果自定义了配置文件，最终会调用`JoranConfigurator`来解析文件，并初始化

定义日志格式，具体内置变量见`PatternLayout`

怎么配置自己的appender呢

分析半天还是看别人的总结来得快
https://www.cnblogs.com/warking/p/5710303.html
还有不懂的就直接看官网
http://logback.qos.ch/documentation.html


