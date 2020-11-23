package com.yan.mall.common.exception;

import com.yan.mall.common.api.IErrorCode;

/**
 * Created with IntelliJ IDEA.
 * Description:自定义异常处理
 * User: Ryan
 * Date: 2020-11-04
 * Time: 14:12
 */
public class ApiException extends RuntimeException{
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
