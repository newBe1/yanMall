package com.yan.mall.admin.component;

import com.yan.mall.admin.service.UmsResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 *资源及对应角色操作组件
 */
@Component
@Slf4j
public class ResourceRoleRulesHolder {
    @Resource
    private UmsResourceService umsResourceService;

    //将数据库中 资源->角色 对应关系存入redis 网关可获取与操作者角色进行比对  服务启动时加载此方法
    @PostConstruct
    public void initResourceRolesMap(){
        log.info("--------------初始化资源角色配置器-------------");
        umsResourceService.initResourceRolesMap();
    }
 }
