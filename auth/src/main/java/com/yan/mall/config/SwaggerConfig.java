package com.yan.mall.config;

import com.yan.mall.common.config.BaseSwaggerConfig;
import com.yan.mall.common.domain.SwaggerProperties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-06
 * Time: 14:52
 */
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.yan.mall.controller")
                .title("mall认证中心")
                .description("mall认证中心相关接口文档")
                .contactName("yan")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
