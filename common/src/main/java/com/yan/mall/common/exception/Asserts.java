package com.yan.mall.common.exception;

import com.yan.mall.common.api.IErrorCode;

/**
 * Created with IntelliJ IDEA.
 * Description: 断言处理 用于抛出任何api 异常
 * User: Ryan
 * Date: 2020-11-04
 * Time: 14:13
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
