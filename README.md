#### 注意
因为这个项目在不断的更新中，所以有些配置文件没有及时更新，导致代码和配置文件（sql等）不符合，请说一下


#### 介绍

这是一个扫码点餐的平台，分为餐厅后台管理系统（vue）和小程序点餐（uniapp）

项目地址：[餐厅后台管理系统](https://gitee.com/lsclinmour/linmour-scan-order-vue)
项目地址：[小程序点餐](https://gitee.com/lsclinmour/linmour-scan-order-uniapp)

商户：账号: 1  密码: 123
管理员：账号: 2  密码: 123

#### 项目架构


#### 技术栈

项目用到的技术和框架列表：

| 框架                                                                                          | 说明               | 版本          |
|---------------------------------------------------------------------------------------------|------------------|-------------|
| [Spring Cloud Alibaba](https://github.com/alibaba/spring-cloud-alibaba)                     | 微服务框架            |2021.0.4.0  |              
| [Nacos](https://github.com/alibaba/nacos)                                                   | 配置中心 & 注册中心      | 2.0.4
| [Kafka](https://github.com/apache/kafka)                                              | 消息队列             | 4.9.4
| [Spring Cloud Gateway](https://github.com/spring-cloud/spring-cloud-gateway)                | 服务网关           |3.4.1
| [MySQL](https://www.mysql.com/cn/)                                                          | 数据库服务器           | 5.7 / 8.0+
| webSocket                                                 | 前后端长连接   |
| [MyBatis Plus](https://mp.baomidou.com/)                                                    | MyBatis 增强工具包    | 3.5.3.1
| [Redis](https://redis.io/)                                                                  | key-value 数据库    | 5.0 / 6.0
| [Spring MVC](https://github.com/spring-projects/spring-framework/tree/master/spring-webmvc) | MVC 框架           | 5.3.24
| [Spring Security](https://github.com/spring-projects/spring-security)                       | Spring 安全框架      | 5.7.5




#### 启动教程

1.  执行sql，创建项目所需要的表
![输入图片说明](.images/sql%E6%96%87%E4%BB%B6.png)

2.上传nacos配置，把这个压缩包上传到自己的nacos上，并且修改里面的redis、mq、  
数据库等配置
<img src=".images/nacos%E9%85%8D%E7%BD%AE.png" width="580">

#### 主要功能展示
<img src=".images/Snipaste_2023-11-30_17-22-25.png" width="580">
<img src=".images/Snipaste_2024-04-10_15-55-35.png" width="580">
<img src=".images/Snipaste_2023-11-30_17-22-37.png" width="580">
<img src=".images/Snipaste_2023-11-30_17-23-17.png" width="580">
<img src=".images/Snipaste_2023-11-30_17-22-56.png" width="580">
