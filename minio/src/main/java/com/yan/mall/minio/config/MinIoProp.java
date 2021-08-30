package com.yan.mall.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-21
 * Time: 10:55
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinIoProp {
    /**
     * 连接url
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accesskey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 桶名
     */
    private String bucketName;
}
