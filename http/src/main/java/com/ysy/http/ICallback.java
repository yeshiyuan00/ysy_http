package com.ysy.http;

import java.net.HttpURLConnection;

/**
 * User: ysy
 * Date: 2015/8/18
 */
public interface ICallback<T> {
    void onSucess(T Result);

    void onFailure(Exception e);

    T parse(HttpURLConnection connection) throws Exception;

    T parse(HttpURLConnection connection, OnProgressUpdatedListener listener) throws Exception;

    void onProgressUpdated(int curLen,int totalLen);
}
