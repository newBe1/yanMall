package com.yan.mall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-05
 * Time: 11:12
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MallGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallGateWayApplication.class, args);
    }
}
