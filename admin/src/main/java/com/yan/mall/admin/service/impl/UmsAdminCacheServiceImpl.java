package com.yan.mall.admin.service.impl;

import com.yan.mall.common.service.RedisService;
import com.yan.mall.model.UmsAdmin;
import com.yan.mall.admin.service.UmsAdminCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UmsAdminCacheServiceImpl  implements UmsAdminCacheService {

    @Autowired
    private RedisService redisService;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;


    @Override
    public void delAdmin(Long adminId) {
        String key = REDIS_DATABASE+":"+REDIS_KEY_ADMIN+":"+adminId;
        redisService.del(key);
    }

    @Override
    public UmsAdmin getAdmin(Long adminId) {
        String key = REDIS_DATABASE+":"+REDIS_KEY_ADMIN+":"+adminId;
        return (UmsAdmin)redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = REDIS_DATABASE+":"+REDIS_KEY_ADMIN+":"+admin.getId();
        redisService.set(key,admin,REDIS_EXPIRE);
    }
}
