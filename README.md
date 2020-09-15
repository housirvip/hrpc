# Hrpc

> Hrpc 全称 housirvip-rpc，是本人在学习 rpc 时的练习代码

Hrpc version 1.0， 是手写 rpc 框架的第一个版本

只是实现了网络通讯，远程调用

![](http://static.nicesite.vip/blog/20200915231642.png)

client 包是客户端源码

server 包是服务端源码

demo 是测试客户端和服务端的 demo，HrpcClient是客户端调用，HrpcServer是服务端调用

TODO

- 引入 Netty 进行 io 多路复用

- 支持服务的注册和发现

- 服务优雅的关闭

- 支持 bean 的容器化管理，实现通过注解注入依赖