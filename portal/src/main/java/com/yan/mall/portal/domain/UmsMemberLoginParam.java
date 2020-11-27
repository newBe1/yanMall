package com.yan.mall.portal.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 * Description: 会员登陆参数
 * User: Ryan
 * Date: 2020-11-27
 * Time: 10:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsMemberLoginParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
