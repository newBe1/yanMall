package com.yan.mall.portal.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yan.mall.common.api.CommonResult;
import com.yan.mall.common.api.ResultCode;
import com.yan.mall.common.constant.AuthConstant;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.common.exception.Asserts;
import com.yan.mall.mapper.UmsMemberMapper;
import com.yan.mall.model.UmsMember;
import com.yan.mall.model.UmsMemberExample;
import com.yan.mall.portal.domain.UmsMemberLoginParam;
import com.yan.mall.portal.service.AuthService;
import com.yan.mall.portal.service.UmsMemberCacheService;
import com.yan.mall.portal.service.UmsMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-27
 * Time: 10:35
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);
    @Resource
    private AuthService authService;

    @Resource
    private UmsMemberMapper memberMapper;

    @Resource
    private HttpServletRequest request;

    @Resource
    private UmsMemberCacheService memberCacheService;

    @Override
    public UmsMember getByUsername(String username) {
        UmsMemberExample memberExample = new UmsMemberExample();
        memberExample.createCriteria().andUsernameEqualTo(username);
        List<UmsMember> members = memberMapper.selectByExample(memberExample);
        if(CollectionUtil.isEmpty(members)){
            return null;
        }
        return members.get(0);
    }

    @Override
    public UmsMember getById(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public void register(String username, String password, String telephone, String authCode) {

    }

    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @Override
    public void updatePassword(String telephone, String password, String authCode) {

    }

    @Override
    public UmsMember getCurrentMember() {
        String userStr = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
        if(StrUtil.isEmpty(userStr)){
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
        UmsMember member = memberCacheService.getMember(userDto.getId());
        if(member == null){
            member = getById(userDto.getId());

            memberCacheService.setMember(member);
        }
        return member;
    }

    @Override
    public void updateIntegration(Long id, Integer integration) {

    }

    @Override
    public UserDto loadUserByUsername(String username) {
        UmsMember member = getByUsername(username);
        if(member!=null){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(member,userDto);

            //TODO  封装用户的角色信息 用于权限判断
            userDto.setRoles(CollectionUtil.toList("前台会员"));
            return userDto;
        }
        return null ;
    }

    @Override
    public CommonResult login(UmsMemberLoginParam umsMemberLoginParam) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.PORTAL_CLIENT_ID);
        params.put("client_secret","123456");
        params.put("grant_type","password");
        params.put("username",umsMemberLoginParam.getUsername());
        params.put("password",umsMemberLoginParam.getPassword());
        return authService.getAccessToken(params);
    }

    /**
     * 校验 验证码
     * @param phoneNum
     * @param authCode
     * @return
     */
    public boolean verifyAuthCode(String phoneNum,String authCode){
        String realAuthCode = memberCacheService.getAuthCode(phoneNum);
        return authCode.equals(realAuthCode);
    }
}
