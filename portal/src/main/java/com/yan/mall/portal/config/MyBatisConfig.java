package com.yan.mall.portal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created with IntelliJ IDEA.
 * Description: MyBatis配置
 * User: Ryan
 * Date: 2020-11-26
 * Time: 14:11
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.yan.mall.mapper","com.yan.mall.portal.dao"})
public class MyBatisConfig {
}
