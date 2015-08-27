package com.ysy.http;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * User: ysy
 * Date: 2015/8/26
 */
public abstract class JsonCallback<T> extends AbstractCallback<T> {

    @Override
    protected T bindData(String result) throws Exception {

        JSONObject json = new JSONObject(result);
        JSONObject data = json.optJSONObject("data");
        Gson gson = new Gson();
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return gson.fromJson(data.toString(), type);
    }
}
