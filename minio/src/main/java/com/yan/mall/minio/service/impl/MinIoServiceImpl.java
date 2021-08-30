package com.yan.mall.minio.service.impl;

import com.yan.mall.common.api.CommonResult;
import com.yan.mall.minio.config.MinIoProp;
import com.yan.mall.minio.domian.MinIoUploadDto;
import com.yan.mall.minio.service.MinIoService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-21
 * Time: 11:13
 */
@Service
public class MinIoServiceImpl implements MinIoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MinIoServiceImpl.class);
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIoProp minIoProp;

    @SneakyThrows
    public MinIoUploadDto uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        //设置存储对象名称
        String objName = sdf.format(new Date()) + "/" + fileName;
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(minIoProp.getBucketName())
                .object(objName)
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE).build();
        minioClient.putObject(putObjectArgs);
        MinIoUploadDto minIoUploadDto = MinIoUploadDto.builder()
                .name(objName)
                .url(minIoProp.getEndpoint() + "/" + minIoProp.getBucketName() + "/" + objName)
                .build();
        LOGGER.info(fileName + "文件上传成功!");
        return minIoUploadDto;
    }

    @SneakyThrows
    public void delFile(String objName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(minIoProp.getBucketName()).object(objName).build());

    }

    @SneakyThrows
    public InputStream downloadFile(String objName) {
        return minioClient.getObject(GetObjectArgs.builder().bucket(minIoProp.getBucketName()).object(objName).build());
    }

    @SneakyThrows
    public String getObjURL(String objName, Integer expires) {
        if(expires != null){
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.POST).bucket(minIoProp.getBucketName()).object(objName).expiry(expires).build());
        }
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.POST).bucket(minIoProp.getBucketName()).object(objName).build());
    }

}
