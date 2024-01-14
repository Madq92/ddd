# DDD 最佳实践

基于DDD思想的开发框架模板，便于快速启动一个项目，减少前期重复造轮子。

### TODO

- [ ] 链路requestId传递
- [ ] 异常设计，全局异常处理
- [ ] Event事件处理
- [ ] integration层demo
- [ ] dao层mybatis-generate插件
- [ ] 唯一序列号生成数据库版本
- [ ] 分布式锁实现服务
- [ ] 文档补充
    - [x] 项目结构
    - [ ] service层设计，processor设计
    - [ ] 事件设计，包含一致性

### 项目结构

![项目结构.png](doc%2F%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84.png)

| module          | 职责                                                             |
|-----------------|----------------------------------------------------------------|
| ddd-bean        | 主要是DTO，请求的入参和回参结构体                                             |
| ddd-start       | 负责spring项目启动                                                   |
| ddd-web         | http api实现，此Module为非必须，与ddd-client二选一或者都要                      |
| ddd-client      | RPC的client，可以是feign，可以是dubble，此Module为非必须，与ddd-client-impl同时出现 |
| ddd-client-impl | RPC的client的实现,此Module为非必须,与ddd-client同时出现                      |
| ddd-service     | 业务服务层，各个领域对外提供的接口，ddd-client-impl和ddd-web的请求都汇聚于此              |
| ddd-core        | 领域核心层                                                          |
| ddd-integration | 外部对接层，外部依赖防腐层                                                  |
| ddd-dao         | 外部对接层，外部依赖防腐层                                                  |
| ddd-common      | 公共层                                                            |

### 领域层

### 领域事件

### service层
