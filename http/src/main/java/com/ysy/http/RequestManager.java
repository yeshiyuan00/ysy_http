package com.ysy.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeshiyuan on 2015/9/19.
 */
public class RequestManager {

    private static RequestManager mRequestManager;
    private HashMap<String, ArrayList<Request>> mCachedRequest;

    public static RequestManager getInstance() {
        if (mRequestManager == null) {
            mRequestManager = new RequestManager();
        }

        return mRequestManager;
    }

    private RequestManager() {
        mCachedRequest = new HashMap<String, ArrayList<Request>>();
    }

    public void performRequest(Request request) {
        RequestTask task = new RequestTask(request);
        task.execute();
        if (!mCachedRequest.containsKey(request.tag)) {
            ArrayList<Request> requests = new ArrayList<Request>();
            mCachedRequest.put(request.tag, requests);
        }
        mCachedRequest.get(request.tag).add(request);
    }

    public void cancelRequest(String tag) {
        if (tag == null || "".equals(tag.trim())) {
            return;
        }
        if (mCachedRequest.containsKey(tag)) {
            ArrayList<Request> requests = mCachedRequest.remove(tag);
            for (Request request : requests) {
                if (!request.isCancelled && tag.equals(request.tag)) {
                    request.cancel();
                }
            }
        }
    }

    public void cancelAll() {
        for (Map.Entry<String, ArrayList<Request>> entry : mCachedRequest.entrySet()) {
            ArrayList<Request> requests = entry.getValue();
            for (Request request : requests) {
                if (!request.isCancelled) {
                    request.cancel();
                }
            }
        }
    }
}
