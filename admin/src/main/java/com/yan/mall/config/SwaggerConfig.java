package com.yan.mall.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.yan.mall.common.config.BaseSwaggerConfig;
import com.yan.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-05
 * Time: 14:02
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {


    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.yan.mall.controller")
                .title("mall后台系统")
                .description("mall后台相关接口文档")
                .contactName("Ryan")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
