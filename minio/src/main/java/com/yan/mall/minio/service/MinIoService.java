package com.yan.mall.minio.service;

import com.yan.mall.common.api.CommonResult;
import com.yan.mall.minio.domian.MinIoUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-12-21
 * Time: 11:12
 */
public interface MinIoService {

    /**
     * 上传
     * @param file
     * @return
     */
    MinIoUploadDto uploadFile(MultipartFile file);

    /**
     * 删除
     * @param objName
     * @return
     */
    void delFile(String objName);

    /**
     * 下载
     * @param objName
     * @return
     */
    InputStream downloadFile(String objName);

    /**
     * 获取外链
     * @param objName
     * @param expires
     * @return
     */
    String getObjURL(String objName,Integer expires);
}
