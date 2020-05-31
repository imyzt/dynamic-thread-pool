# 介绍

​	看了美团  [Java线程池实现原理及其在美团业务中的实践](https://mp.weixin.qq.com/s/baYuX8aCwQ9PP6k7TDl2Ww) 这篇文章之后，对线程池有了一些新的认识，后面看到了 [如何设置线程池参数？美团给出了一个让面试官虎躯一震的回答](https://mp.weixin.qq.com/s/9HLuPcoWmTqAeFKa1kj-_A) 四川好男人的一顿骚操作，瞬间又认识的更清晰了一些。就想着能不能做一套这样的线程池管理系统，然后就有了后文。

​	Web应用系统中，ThreadPoolExecutor一般是由代码硬编码的，core/max/queueSize都是定数，而系统的流量不是一个定数。在峰值期间，线程池无法动态扩容，导致大量请求处理失败进行拒绝策略。本系统主要是做了一个可以动态调参的线程池，依赖ThreadPoolExecutor本身提供的set方法，对运行中的各项基本参数进行动态调控。并且包括监控报警、Web界面、自动扩容策略等。

# 设计

![架构图](http://blog.imyzt.top/upload/2020/05/v9c4k50u7uhduqh0ptvq1c14d2.png)

# 实现

## 客户端

配置文件需配置服务端地址

线程池配置需配置线程池（三种接入方式）


1. 接入方式， 提供三种[##feature]

2. 启动时，携带应用名上报对应的线程池配置(多实例同时启动， 此接口需要幂等)

3. 通过Java定时器，定时上报线程池数据。

4. 与服务端维持长连接，60秒一次，服务端线程池配置（web操作）变化时，返回对应发生改变的[线程池名称]。然后客户端请求服务端获取对应线程池新的配置。



## 服务端



1. 上报接口  

   1.1 配置上报接口(配置带版本号)  

      1.1.1 初次上报(数据库无法查询, 即视为初次上报)， 存储初始化参数  

      1.1.2 服务端Web发起调参, 自行记录， 无需客户端上报(可优化为客户端上报)

   1.2 工作状态上报接口  

   ​	1.2.1 定时上报应用每个线程池各项指标

2. 发起调参后，接受客户端请求最新线程池参数请求

3. 存储上报数据， 负责提供数据展示接口，读取数据



## feature

1. 接入方式新增, 除了Dynamic，让默认的Spring的task和jdk的executor都支持，只有dynamic支持修改队列大小。

## todo-list
[https://github.com/imyzt/dynamic-thread-pool/projects/1](https://github.com/imyzt/dynamic-thread-pool/projects/1)