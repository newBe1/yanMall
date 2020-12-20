package com.yan.mall.minio.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.yan.mall.common.api.CommonResult;
import com.yan.mall.minio.domian.BucketPolicyConfigDto;
import com.yan.mall.minio.domian.MinIoUploadDto;
import io.minio.*;
import io.minio.messages.DeleteObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController("minIo")
@Api(tags = "MinIo对象存储操作管理")
public class MinIoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MinIoController.class);

    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    @ApiOperation("文件上传")
    @RequestMapping(value = "uploadFile",method = RequestMethod.POST)
    public CommonResult<MinIoUploadDto> uploadFile(@RequestParam(value = "file",required = true)MultipartFile file){
        //创建一个MinIo的Java客户端
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY,SECRET_KEY)
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());

            if(isExist){
                LOGGER.info(BUCKET_NAME+"存储桶已存在");
            }else {
                //创建存储桶 并设置只读权限
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
                BucketPolicyConfigDto bucketPolicyConfigDto = createBucketPolicyConfigDto(BUCKET_NAME);
                SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs.builder()
                        .bucket(BUCKET_NAME)
                        .config(JSONUtil.toJsonStr(bucketPolicyConfigDto))
                        .build();
                minioClient.setBucketPolicy(setBucketPolicyArgs);
                LOGGER.info(BUCKET_NAME+"存储桶创建完成");
            }
            String fileName = file.getOriginalFilename();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

            //设置存储对象名称
            String objName = simpleDateFormat.format(new Date())+"/"+fileName;

            //使用putObject上传一个文件到存储桶中
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(),file.getSize(),ObjectWriteArgs.MIN_MULTIPART_SIZE)
                    .build();
            minioClient.putObject(putObjectArgs);
            LOGGER.info(fileName+"文件上传成功!");

            MinIoUploadDto minIoUploadDto = MinIoUploadDto.builder()
                    .name(fileName)
                    .url(ENDPOINT + "/" + BUCKET_NAME + "/" + objName)
                    .build();

            return CommonResult.success(minIoUploadDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("上传发生错误: {}！", e.getMessage());
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除文件")
    @RequestMapping(value = "delFile",method = RequestMethod.POST)
    public CommonResult delFile(@RequestParam(value = "objNames",required = true)String objNames){
        String[] removeObjArr = objNames.split(",");
        List<String> keys = Arrays.asList(removeObjArr);
        List<DeleteObject> removeObjList = new ArrayList<>();
        keys.forEach(s -> {
            removeObjList.add(new DeleteObject(s));
        });
        Iterator<DeleteObject> it = removeObjList.iterator();

        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY,SECRET_KEY)
                    .build();
            minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(BUCKET_NAME).objects((Iterable<DeleteObject>) it).build());
            return CommonResult.success(objNames+"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.failed();
    }

    @ApiOperation("文件下载")
    @RequestMapping(value = "downLoadFile",method = RequestMethod.POST)
    public CommonResult downLoadFile(@RequestParam(value = "objName",required = true)String objName){
        try {
            MinioClient minioClient = MinioClient.builder().
                    endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY,SECRET_KEY)
                    .build();
            InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(BUCKET_NAME).object(objName).build());
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
                System.out.println(new String(buf, 0, bytesRead, StandardCharsets.UTF_8));
            }
            stream.close();
            return CommonResult.success(objName+"下载成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return CommonResult.failed();
    }
    private BucketPolicyConfigDto createBucketPolicyConfigDto(String bucketName) {
        BucketPolicyConfigDto.Statement statement = BucketPolicyConfigDto.Statement.builder()
                .Effect("Allow")
                .Principal("*")
                .Action("s3:GetObject")
                .Resource("arn:aws:s3:::"+bucketName+"/*.**").build();
        return BucketPolicyConfigDto.builder()
                .Version("2020-12-17")
                .Statement(CollUtil.toList(statement))
                .build();
    }
}
