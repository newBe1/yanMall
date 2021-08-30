package com.yan.mall.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-05
 * Time: 14:30
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.yan.mall.mapper","com.yan.mall.admin.dao"})
public class MybatisConfig {
}
