package com.yan.mall.admin.dao;

import com.yan.mall.admin.validator.FlagValidator;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-23
 * Time: 15:33
 */
@Data
public class TestParam {
    @NotEmpty
    private String username;
    @FlagValidator(value = {"0","1"},message = "显示状态不正确")
    private Integer status;
    private String password;
    @Min(10)
    private Integer age;

}
