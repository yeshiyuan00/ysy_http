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
    private volatile boolean isCancelled;

    @Override
    public T parse(HttpURLConnection connection) throws AppException {
        return parse(connection, null);
    }

    @Override
    public T parse(HttpURLConnection connection, OnProgressUpdatedListener listener) throws AppException {
        try {
            checkIfCancelled();
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                if (path == null) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    InputStream is = connection.getInputStream();
                    byte[] buff = new byte[2048];
                    int len;
                    while ((len = is.read(buff)) != -1) {
                        checkIfCancelled();
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
                        checkIfCancelled();
                        out.write(buff, 0, len);
                        CurLen += len;
                        listener.onProgressUpdated(CurLen, TotalLen);
                    }
                    is.close();
                    out.flush();
                    out.close();
                    return bindData(path);
                }
            } else {
                throw new AppException(status, connection.getResponseMessage());
            }
        } catch (Exception e) {
            throw new AppException(AppException.ErrorType.SERVER, e.getMessage());
        }

    }

    protected abstract T bindData(String result) throws AppException;

    protected void checkIfCancelled() throws AppException {
        if (isCancelled){
            throw new AppException(AppException.ErrorType.CANCEL,"the request has been cancelled");
        }
    }
    @Override
    public void cancel() {
        isCancelled = true;
    }

    public ICallback setCachePath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public void onProgressUpdated(int curLen, int totalLen) {

    }

    ;
}
