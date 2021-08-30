package com.yan.mall.minio.config;

import com.yan.mall.common.config.BaseSwaggerConfig;
import com.yan.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.yan.mall.minio.controller")
                .title("MinIo存储对象系统")
                .description("MinIo存储对象相关接口文档")
                .contactName("Ryan")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
