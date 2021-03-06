package com.ysy.http;

import java.util.Map;

/**
 * User: ysy
 * Date: 2015/8/18
 */
public class Request {
    public ICallback iCallback;
    public boolean enableProgressUpdated = false;
    public OnGlobalExceptionListener onGlobalExceptionListener;
    public String tag;

    public void setCallback(ICallback iCallback) {
        this.iCallback = iCallback;
    }

    public void setGlobalExceptionListener(OnGlobalExceptionListener onGlobalExceptionListener) {
        this.onGlobalExceptionListener = onGlobalExceptionListener;
    }

    public void enableProgressUpdated(boolean enable) {
        this.enableProgressUpdated = enable;
    }

    public void checkIfCancelled() throws AppException {
        if (isCancelled) {
            throw new AppException(AppException.ErrorType.CANCEL, "the request has been cancelled");
        }
    }

    public void cancel() {
        isCancelled = true;
        iCallback.cancel();
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public enum RequestMethod {
        GET, POST, PUT, DELETE
    }

    public int maxRetryCount = 3;

    public String url;
    public String content;
    public Map<String, String> headers;

    public volatile boolean isCancelled;

    public RequestMethod method;

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }


    public Request(String url, String content) {
        this.url = url;
        this.content = content;
    }

    public Request(String url) {
        this.url = url;
    }
}
