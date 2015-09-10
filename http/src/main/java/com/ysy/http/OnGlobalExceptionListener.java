package com.ysy.http;

/**
 * User: ysy
 * Date: 2015/9/10
 * Time: 8:24
 */
public interface OnGlobalExceptionListener {
    boolean handleException(AppException exception);
}
