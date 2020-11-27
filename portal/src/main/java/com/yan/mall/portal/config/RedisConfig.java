package com.yan.mall.portal.config;

import com.yan.mall.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:redis配置
 * User: Ryan
 * Date: 2020-11-26
 * Time: 14:43
 */
@Configuration
@EnableCaching   //开启缓存注解
public class RedisConfig extends BaseRedisConfig {
}
