package com.yan.mall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-23
 * Time: 11:44
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MallSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallSearchApplication.class, args);
    }
}
