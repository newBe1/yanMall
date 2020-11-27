package com.yan.mall.admin.service.impl;

import com.yan.mall.common.constant.AuthConstant;
import com.yan.mall.common.service.RedisService;
import com.yan.mall.mapper.UmsResourceMapper;
import com.yan.mall.mapper.UmsRoleMapper;
import com.yan.mall.mapper.UmsRoleResourceRelationMapper;
import com.yan.mall.model.*;
import com.yan.mall.admin.service.UmsResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UmsResourceServiceImpl implements UmsResourceService {
    @Resource
    private RedisService redisService;

    @Resource
    private UmsResourceMapper umsResourceMapper;

    @Resource
    private UmsRoleMapper umsRoleMapper;

    @Resource
    private UmsRoleResourceRelationMapper umsRoleResourceRelationMapper;

    @Value("${spring.application.name}")
    private String applicationName;


    @Override
    public Map<String, List<String>> initResourceRolesMap() {
        Map<String, List<String>> resourceRoleMap = new HashMap<>();
        List<UmsResource> umsResourceList = umsResourceMapper.selectByExample(new UmsResourceExample());
        List<UmsRole> umsRoleList = umsRoleMapper.selectByExample(new UmsRoleExample());
        List<UmsRoleResourceRelation> umsRoleResourceRelationList = umsRoleResourceRelationMapper.selectByExample(new UmsRoleResourceRelationExample());
        for (UmsResource resource : umsResourceList) {
            Set<Long> roleIds = umsRoleResourceRelationList.stream().filter(item -> item.getResourceId().equals(resource.getId())).map(UmsRoleResourceRelation::getRoleId).collect(Collectors.toSet());
            List<String> roleNames = umsRoleList.stream().filter(item -> roleIds.contains(item.getId())).map(item -> item.getId() + "_" + item.getName()).collect(Collectors.toList());
            resourceRoleMap.put("/"+applicationName+resource.getUrl(),roleNames);
            log.info("-----------------"+"/"+applicationName+resource.getUrl()+roleNames+"--------------");
        }
        redisService.del(AuthConstant.RESOURCE_ROLES_MAP_KEY);
        redisService.hSetAll(AuthConstant.RESOURCE_ROLES_MAP_KEY, resourceRoleMap);
        return resourceRoleMap;
    }
}
