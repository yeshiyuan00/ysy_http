package com.ysy.http;

import com.google.gson.stream.JsonReader;

/**
 * Created by Stay on 15/7/15.
 * Powered by www.stay4it.com
 */
public interface IEntity {
    void readFromJson(JsonReader reader) throws  AppException;
}
