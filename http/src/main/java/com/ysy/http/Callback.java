package com.ysy.http;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * User: ysy
 * Date: 2015/8/26
 */
public abstract class Callback<T> implements ICallback<T> {
    private Class<T> clz;

    @Override
    public T parse(HttpURLConnection connection) throws Exception {
        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream is = connection.getInputStream();
            byte[] buff = new byte[2048];
            int len;
            while ((len = is.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
            is.close();
            out.flush();
            out.close();
            String result = new String(out.toByteArray());
            JSONObject json = new JSONObject(result);
            JSONObject data = json.optJSONObject("data");
            Gson gson = new Gson();
            System.out.println("result="+result);
            return gson.fromJson(data.toString(), clz);
        }
        return null;
    }

    public ICallback setReturnType(Class<T> clz) {
        this.clz = clz;
        return this;
    }
}
