package com.yan.mall.admin.service;

import com.yan.mall.admin.domain.OssCallbackResult;
import com.yan.mall.admin.domain.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description: oss文件上传
 * User: Ryan
 * Date: 2020-11-26
 * Time: 18:22
 */
public interface OssService {
    /**
     * 文件上传策略生成
     * @return
     */
    OssPolicyResult policy();

    /**
     * 文件上传接口回调
     * @param request
     * @return
     */
    OssCallbackResult callback(HttpServletRequest request);
}
