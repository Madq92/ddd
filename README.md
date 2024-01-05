# DDD 最佳实践

### 项目结构

![项目结构.png](doc%2F%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84.png)

##### ddd-bean

主要是DTO，请求的入参和回参结构体

##### ddd-start

负责spring项目启动

##### ddd-web

http api实现，此Module为非必须，与ddd-client二选一或者都要

##### ddd-client

RPC的client，可以是feign，可以是dubble，此Module为非必须，与ddd-client-impl同时出现

##### ddd-client-impl

RPC的client的实现,此Module为非必须,与ddd-client同时出现

##### ddd-service

核心服务层，各个领域对外提供的接口，ddd-client-impl和ddd-web的请求都汇聚于此

##### ddd-integration

外部对接层，外部依赖防腐层

##### ddd-dao

数据库依赖层

##### ddd-common

公共层
