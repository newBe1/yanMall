package com.yan.mall.minio.controller;


import com.yan.mall.common.api.CommonResult;
import com.yan.mall.minio.domian.MinIoUploadDto;
import com.yan.mall.minio.service.MinIoService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("minio")
@Api(tags = "MinIo对象存储操作管理")
public class MinIoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MinIoController.class);

    @Resource
    private MinIoService minIoService;

    @ApiOperation("文件上传")
    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public CommonResult<MinIoUploadDto> uploadFile(@ApiParam(name = "file", value = "文件MultipartFile格式") MultipartFile file) {
        try {
            MinIoUploadDto minIoUploadDto = minIoService.uploadFile(file);
            return CommonResult.success(minIoUploadDto);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("文件上传失败",e);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除文件")
    @RequestMapping(value = "delFile", method = RequestMethod.POST)
    @ApiImplicitParam(name = "objName" , value = "文件对象名称" , required = true)
    public CommonResult delFile(String objName) {
        try {
            minIoService.delFile(objName);
            LOGGER.info(objName + "文件删除成功");
            return CommonResult.success(objName + "文件删除成功");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(objName + "文件删除失败",e);
            return CommonResult.failed();
        }

    }

    @ApiOperation("文件下载")
    @RequestMapping(value = "downLoadFile", method = RequestMethod.POST)
    @ApiImplicitParam(name = "objName" , value = "文件对象名称" , required = true)
    public void downLoadFile(String objName,
                                     HttpServletResponse response, HttpServletRequest request) {
        javax.servlet.ServletOutputStream out = null;
        try{
            InputStream inputStream = minIoService.downloadFile(objName);
            response.setHeader("Content-Disposition", "attachment;filename=" + objName.substring(objName.lastIndexOf("/") + 1));
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            out = response.getOutputStream();
            byte[] content = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(content)) != -1) {
                out.write(content, 0, length);
            }
            out.flush();
            LOGGER.info(objName + "文件下载成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("文件流关闭异常", e);
                }
            }
        }
    }

    @ApiOperation("获取外链")
    @RequestMapping(value = "getObjURL", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objName", value = "文件对象名称", required = true),
            @ApiImplicitParam(name = "expires", value = "外链有效时间（单位：秒）", required = false),
    })
    public CommonResult getObjURL(String objName,Integer expires){
        try {
            String str = minIoService.getObjURL(objName, expires);
            return CommonResult.success(objName + "文件的访问路径：" + str +" 有效期(S)：" + expires);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.failed();
        }
    }
}