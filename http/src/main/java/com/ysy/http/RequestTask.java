package com.ysy.http;

import android.os.AsyncTask;

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
    protected void onPostExecute(Object object) {
        super.onPostExecute(object);
        if (object instanceof Exception) {
            request.iCallback.onFailure((Exception) object);
        } else {
            request.iCallback.onSucess((String) object);
        }
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            return HttpUrlConnectionUtil.excute(request);
        } catch (Exception e) {
            return e;
        }
    }
}
