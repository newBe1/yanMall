package com.yan.mall.search.config;

import com.yan.mall.common.config.BaseSwaggerConfig;
import com.yan.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-23
 * Time: 11:53
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.yan.mall.search.controller")
                .title("搜索系统")
                .description("搜索相关接口文档")
                .contactName("Ryan")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
