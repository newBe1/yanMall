package com.yan.mall.portal.config;

import com.yan.mall.common.config.BaseSwaggerConfig;
import com.yan.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-26
 * Time: 14:37
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.yan.mall.portal.controller")
                .title("mall客户端系统")
                .description("mall客户端接口文档")
                .contactName("Ryan")
                .version("1.0")
                .enableSecurity(true).build();
    }
}
