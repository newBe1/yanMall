package com.yan.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-05
 * Time: 11:12
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableWebFlux
public class MallGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallGateWayApplication.class, args);
    }
}
