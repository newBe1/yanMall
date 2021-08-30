package com.yan.mall.portal.service.impl;

import com.yan.mall.common.service.RedisService;
import com.yan.mall.model.UmsMember;
import com.yan.mall.portal.service.UmsMemberCacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-27
 * Time: 11:19
 */
@Service
public class UmsMemberCacheServiceImpl implements UmsMemberCacheService {
    @Resource
    private RedisService redisService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.expire.authCode}")
    private Long REDIS_EXPIRE_AUTH_CODE;
    @Value("${redis.key.member}")
    private String REDIS_KEY_MEMBER;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_AUTH_CODE;
    
    @Override
    public void delMember(Long memberId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + "" + memberId;
        redisService.del(key);
    }

    @Override
    public UmsMember getMember(Long memberId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + "" + memberId;
        return (UmsMember)redisService.get(key);
    }

    @Override
    public void setMember(UmsMember member) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + "" + member.getId();
        redisService.set(key,member,REDIS_EXPIRE);
    }

    @Override
    public void setAuthCode(String phoneNum, String authCode) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + phoneNum;
        redisService.set(key, authCode , REDIS_EXPIRE_AUTH_CODE);
    }

    @Override
    public String getAuthCode(String phoneNum) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + phoneNum;
        return (String) redisService.get(key);
    }
}
