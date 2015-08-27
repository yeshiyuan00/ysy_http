package com.ysy.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * User: ysy
 * Date: 2015/8/27
 */
public abstract class AbstractCallback<T> implements ICallback<T> {
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
            return bindData(result);
        }
        return null;
    }

    protected abstract T bindData(String result) throws Exception;
}
