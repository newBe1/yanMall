package com.yan.mall.auth.service.impl;

import com.yan.mall.auth.constant.MessageConstant;
import com.yan.mall.auth.domain.SecurityUser;
import com.yan.mall.auth.service.UmsAdminService;
import com.yan.mall.auth.service.UmsMemberService;
import com.yan.mall.common.constant.AuthConstant;
import com.yan.mall.common.domain.UserDto;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:通过用户名获取用户UserDto对象 并封装SecurityUser将其加载入security（SecurityUser 实现 UserDetails）
 * User: Ryan
 * Date: 2020-11-06
 * Time: 15:31
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    @Resource
    private UmsAdminService adminService;
    @Resource
    private UmsMemberService memberService;
    @Resource
    private HttpServletRequest request;

    /**
     * security 通过输入的用户名 去数据库查询用户详情信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = request.getParameter("client_id");
        UserDto userDto;
        if(AuthConstant.ADMIN_CLIENT_ID.equals(clientId)){                   //后台管理用户登陆
            userDto = adminService.loadUserByUsername(username);
        }else {
            userDto = memberService.loadUserByUsername(username);            //客户端登陆
        }
        if(userDto == null){
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }

        userDto.setClientId(clientId);
        SecurityUser securityUser = new SecurityUser(userDto);    //SecurityUser实现了UserDetails 为UserDetails子类

        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }
}
