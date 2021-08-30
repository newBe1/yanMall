package com.yan.mall.auth.domain;

import com.yan.mall.common.domain.UserDto;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Description:登录用户信息 存入security 中的用户对象
 * User: Ryan
 * Date: 2020-11-06
 * Time: 14:47
 *
 * UserDetails接口说明：
 *   用户的权限集， 默认需要添加   ROLE_   前缀  security会自动截取 ROLE_ 后的字符串作为实际权限字符串
 *   用户的加密后的密码， 不加密会使用{noop}前缀
 *   应用内唯一的用户名
 *   账户是否过期
 *   账户是否锁定
 *   凭证是否过期
 *   用户是否可用
 */
@Data
public class SecurityUser implements UserDetails{
    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 登录客户端ID
     */
    private String clientId;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser(UserDto userDto) {
        this.setId(userDto.getId());
        this.setUsername(userDto.getUsername());
        this.setPassword(userDto.getPassword());
        this.setEnabled(userDto.getStatus() == 1);
        this.setClientId(userDto.getClientId());
        if (userDto.getRoles() != null) {
            authorities = new ArrayList<>();
            userDto.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
        }
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
