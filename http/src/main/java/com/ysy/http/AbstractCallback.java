package com.ysy.http;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * User: ysy
 * Date: 2015/8/27
 */
public abstract class AbstractCallback<T> implements ICallback<T> {
    private String path;

    @Override
    public T parse(HttpURLConnection connection) throws Exception {
        return parse(connection, null);
    }

    @Override
    public T parse(HttpURLConnection connection, OnProgressUpdatedListener listener) throws Exception {
        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            if (path == null) {
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
            } else {
                FileOutputStream out = new FileOutputStream(path);
                InputStream is = connection.getInputStream();
                int TotalLen = connection.getContentLength();
                int CurLen = 0;
                byte[] buff = new byte[2048];
                int len;
                while ((len = is.read(buff)) != -1) {
                    out.write(buff, 0, len);
                    CurLen += len;
                    listener.onProgressUpdated(CurLen, TotalLen);
                }
                is.close();
                out.flush();
                out.close();
                return bindData(path);
            }
        }
        return null;
    }

    protected abstract T bindData(String result) throws Exception;

    public ICallback setCachePath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public void onProgressUpdated(int curLen, int totalLen) {

    }

    ;
}
