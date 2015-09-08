package com.ysy.http;

import java.net.HttpURLConnection;

/**
 * User: ysy
 * Date: 2015/8/18
 */
public interface ICallback<T> {
    void onSucess(T Result);

    void onFailure(AppException e);

    T parse(HttpURLConnection connection) throws AppException;

    T parse(HttpURLConnection connection, OnProgressUpdatedListener listener) throws AppException;

    void onProgressUpdated(int curLen, int totalLen);
}
