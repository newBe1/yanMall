package com.yan.mall.minio.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.yan.mall.minio.domian.BucketPolicyConfigDto;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-21
 * Time: 10:58
 */
@Configuration
@EnableConfigurationProperties(MinIoProp.class)
public class MinIoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MinIoConfiguration.class);
    @Autowired
    private MinIoProp minIoProp;

    @Bean
    public MinioClient minioClient(){
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minIoProp.getEndpoint())
                    .credentials(minIoProp.getAccesskey(),minIoProp.getSecretKey())
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minIoProp.getBucketName()).build());
            if(isExist){
                LOGGER.info(minIoProp.getBucketName()+"存储桶已存在");
            }else {
                //创建存储桶 并设置只读权限
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minIoProp.getBucketName()).build());
                BucketPolicyConfigDto bucketPolicyConfigDto = createBucketPolicyConfigDto(minIoProp.getBucketName());
                SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs.builder()
                        .bucket(minIoProp.getBucketName())
                        .config(JSONUtil.toJsonStr(bucketPolicyConfigDto))
                        .build();
                minioClient.setBucketPolicy(setBucketPolicyArgs);
                LOGGER.info(minIoProp.getBucketName()+"存储桶创建完成");
            }
            return minioClient;
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("存储桶创建失败");
        }
        return null;
    }

    private BucketPolicyConfigDto createBucketPolicyConfigDto(String bucketName) {
        BucketPolicyConfigDto.Statement statement = BucketPolicyConfigDto.Statement.builder()
                .Effect("Allow")
                .Principal("*")
                .Action("s3:GetObject")
                .Resource("arn:aws:s3:::"+bucketName+"/*.**").build();
        return BucketPolicyConfigDto.builder()
                .Version("V1.0")
                .Statement(CollUtil.toList(statement))
                .build();
    }
}
