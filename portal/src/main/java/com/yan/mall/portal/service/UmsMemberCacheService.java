package com.yan.mall.portal.service;

import com.yan.mall.model.UmsMember;

/**
 * Created with IntelliJ IDEA.
 * Description: 客户端缓存业务类
 * User: Ryan
 * Date: 2020-11-27
 * Time: 11:18
 */
public interface UmsMemberCacheService {
    /**
     * 删除会员用户缓存
     */
    void delMember(Long memberId);

    /**
     * 获取会员用户缓存
     */
    UmsMember getMember(Long memberId);

    /**
     * 设置会员用户缓存
     */
    void setMember(UmsMember member);

    /**
     * 设置验证码
     */
    void setAuthCode(String phoneNum, String authCode);

    /**
     * 获取验证码
     */
    String getAuthCode(String phoneNum);
}
