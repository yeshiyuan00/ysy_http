package com.ysy.http;

import android.os.AsyncTask;

import java.net.HttpURLConnection;

/**
 * User: ysy
 * Date: 2015/8/18
 */
public class RequestTask extends AsyncTask<Void, Integer, Object> {
    private Request request;

    public RequestTask(Request request) {
        this.request = request;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof Exception) {
            request.iCallback.onFailure((Exception) o);
        } else {
            request.iCallback.onSucess(o);
        }
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            HttpURLConnection connection = HttpUrlConnectionUtil.excute(request);
            return request.iCallback.parse(connection);
        } catch (Exception e) {
            return e;
        }
    }
}