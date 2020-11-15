package com.yan.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-13
 * Time: 11:32
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.yan.mall")
public class MallPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallPortalApplication.class, args);
    }
}
