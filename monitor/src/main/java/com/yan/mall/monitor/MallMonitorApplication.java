package com.yan.mall.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-06
 * Time: 10:04
 *
 * Spring Boot Admin是一个开源社区项目，用于管理和监控SpringBoot应用程序。 应用程序作为Spring Boot Admin Client向为Spring Boot Admin Server注册（通过HTTP）或使用SpringCloud注册中心（例如Eureka，Nacos）发现。 UI是的Vue.js应用程序，展示Spring Boot Admin Client的Actuator端点上的一些监控。常见的功能如下：
 *
 * 显示健康状况
 * 显示详细信息，例如
 * JVM和内存指标
 * micrometer.io指标
 * 数据源指标
 * 缓存指标
 * 显示内部信息
 * 关注并下载日志文件
 * 查看JVM系统和环境属性
 * 查看Spring Boot配置属性
 * 支持Spring Cloud的可发布/ env-和// refresh-endpoint
 * 轻松的日志级别管理
 * 与JMX-beans交互
 * 查看线程转储
 * 查看http-traces
 * 查看审核事件
 * 查看http端点
 * 查看预定的任务
 * 查看和删除活动会话（使用spring-session）
 * 查看Flyway / Liquibase数据库迁移
 * 下载heapdump
 * 状态更改通知（通过电子邮件，Slack，Hipchat等）
 * 状态更改的事件日志（非持久性）
 */
@EnableDiscoveryClient
@EnableAdminServer
@SpringBootApplication(scanBasePackages ={"com.yan.mall.monitor.*"})
public class MallMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallMonitorApplication.class, args);
    }
}
