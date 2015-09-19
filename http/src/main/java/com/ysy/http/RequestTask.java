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
        if (o instanceof AppException) {
            if (request.onGlobalExceptionListener != null) {
                if (!request.onGlobalExceptionListener.handleException((AppException) o)) {
                    request.iCallback.onFailure((AppException) o);
                }
            }else {
                request.iCallback.onFailure((AppException) o);
            }
        } else {
            request.iCallback.onSuccess(o);
        }
    }

    @Override
    protected Object doInBackground(Void... params) {
        return request(0);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public Object request(int retry) {
        try {
            isCancelled();
            HttpURLConnection connection = HttpUrlConnectionUtil.excute(request);
            if (request.enableProgressUpdated) {
                return request.iCallback.parse(connection, new OnProgressUpdatedListener() {
                    @Override
                    public void onProgressUpdated(int curLen, int totalLen) {
                        publishProgress(curLen, totalLen);
                    }
                });
            } else {
                return request.iCallback.parse(connection);
            }
        } catch (AppException e) {
            if (e.type == AppException.ErrorType.TIMEOUT) {
                if (retry < request.maxRetryCount) {
                    retry++;
                    return request(retry);
                }
            }
            return e;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        request.iCallback.onProgressUpdated(values[0], values[1]);
    }
}
