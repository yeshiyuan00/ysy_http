package com.ysy.http;

import java.util.Map;

/**
 * User: ysy
 * Date: 2015/8/18
 */
public class Request {
    public ICallback iCallback;
    public boolean enableProgressUpdated = false;

    public void setCallback(ICallback iCallback) {
        this.iCallback = iCallback;
    }

    public void enableProgressUpdated(boolean enable) {
        this.enableProgressUpdated = enable;
    }

    public enum RequestMethod {
        GET, POST, PUT, DELETE
    }

    public String url;
    public String content;
    public Map<String, String> headers;

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
