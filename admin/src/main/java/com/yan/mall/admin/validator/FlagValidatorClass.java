package com.yan.mall.admin.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * Description:状态标记校验器
 * User: Ryan
 * Date: 2020-11-05
 * Time: 16:04
 */
//实现ConstraintValidator 完成自定义校验注解
public class FlagValidatorClass implements ConstraintValidator<FlagValidator,Integer> {
    private String[] values;

    @Override
    public void initialize(FlagValidator flagValidator) {
        this.values = flagValidator.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = false;
        if (value == null){
            isValid = false;
        }

        for (int i = 0; i < values.length; i++) {
            if(values[i].equals(String.valueOf(value))){
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}
