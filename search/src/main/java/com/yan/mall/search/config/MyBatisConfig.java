package com.yan.mall.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-23
 * Time: 11:52
 */
@Configuration
@MapperScan({"com.yan.mall.mapper","com.yan.mall.search.dao"})
public class MyBatisConfig {
}
