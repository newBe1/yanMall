package com.yan.mall.admin.controller;

import com.yan.mall.admin.domain.OssCallbackResult;
import com.yan.mall.admin.service.OssService;
import com.yan.mall.admin.service.impl.OssServiceImpl;
import com.yan.mall.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-26
 * Time: 18:34
 */
@Api(tags = "oss操作接口")
@RestController
@RequestMapping("/aliyun/oss")
public class OssController {
    @Resource
    private OssService ossService;

    @ApiOperation("oss上传签名生成")
    @RequestMapping(value = "/policy",method = RequestMethod.GET)
    public CommonResult policy(){
        return CommonResult.success(ossService.policy());
    }

    @ApiOperation(value = "oss上传成功回调")
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return CommonResult.success(ossCallbackResult);
    }

}
