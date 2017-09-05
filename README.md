# cjbase
通用JAVA基础代码

介绍
--
- git clone https://github.com/panjingang/cjbase.git
- cd cjbase && mvn install

模块
--
* [cjbase-api]  - API接口
* [cjbase-biz]  - 业务基础
* [cjbase-core] - 核心代码，上下文、基础异常类、枚举类
* [cjbase-datasource] -数据源，多租户数据源
* [cjbase-http]  - HTTP请求
* [cjbse-lock] - 分布式锁, Redis和Zookeeper
* [cjbase-mybatis] - Mybatis通用增删改查
* [cjbase-push] - 推送服务
* [cjbase-redis] - Redis,基于Spring-data-redis
* [cjbase-validate] - API接口参数校验
* [cjbase-util] - 通用辅助类
* [cjbase-zookeeper] - Zookeeper节点创建、删除、订阅功能

使用指南
--
> API接口
  ```
  <dependency>
     <groupId>com.github.panjingang</groupId>
     <artifactId>cjbase-api</artifactId>
     <version>1.0.0-SNAPSHOT</version>
  </<dependency>
  ```
> 业务基础

    <dependency>
       <groupId>com.github.panjingang</groupId>
       <artifactId>cjbase-biz</artifactId>
       <version>1.0.0-SNAPSHOT</version>
    </<dependency>
