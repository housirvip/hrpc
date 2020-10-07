# Hrpc

> Hrpc 全称 housirvip-rpc，用于学习 rpc 源码编写

Hrpc version 1.1

- 客户端动态代理

- 服务端反射调用

- 实现了网络通讯，远程调用

- 服务端 BIO 模型引入线程池

- 服务端 IO 多路复用（ select 模型 ）

- 支持 IoC 容器化管理，实现通过注解注入依赖

- 服务器 shutdown hook

- 支持 json 序列化方式

![](http://static.nicesite.vip/blog/20200915231642.png)

consumer 包是消费端（客户端）源码

provider 包是生产端（服务端）源码

ioc 包是 IoC 容器源码

demo 包是测试客户端和服务端的 demo

TODO

- PlainNio 引入 worker 和 boss 线程

- 引入 Netty 强化 IO 多路复用

- 支持服务的注册和发现

- 支持 protobuf 