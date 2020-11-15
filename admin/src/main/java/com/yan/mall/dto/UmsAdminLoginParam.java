package com.yan.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-11
 * Time: 15:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminLoginParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
