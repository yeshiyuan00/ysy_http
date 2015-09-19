package com.ysy.ysy_http;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ysy.http.AppException;
import com.ysy.http.FileCallback;
import com.ysy.http.JsonCallback;
import com.ysy.http.Request;
import com.ysy.http.RequestTask;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button mRunOnSubThreadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRunOnSubThreadBtn = (Button) findViewById(R.id.mRunOnSubThreadBtn);
        mRunOnSubThreadBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mRunOnSubThreadBtn:
                //testHttpPostOnSubThread();
                //testHttpPostOnSubThreadForGeneric();
                //testHttpPostOnSubThreadForDownload();
//                testHttpPostOnSubThreadForDownloadProgress();
                testHttpPostOnSubThreadForDownloadProgressCancelTest();
                break;
        }
    }

    private void testHttpPostOnSubThread() {
        String url = "http://api.stay4it.com/v1/public/core/?service=user.login";
        String content = "account=stay4it&password=123456";
        Request request = new Request(url, Request.RequestMethod.POST);

        request.setCallback(new JsonCallback<String>() {

            @Override
            public void onSuccess(String Result) {
                Log.e("stay", "testHttpGet return:" + Result);
            }

            @Override
            public void onFailure(AppException e) {
                if (e.statusCode == 403) {
                    if ("password incorrect".equals(e.responseMessage)) {
//                        TODO
                    } else if ("token invalid".equals(e.responseMessage)) {
//                        TODO relogin
                    }
                }
                e.printStackTrace();
            }

        });
        request.setGlobalExceptionListener(this);
        request.content = content;
        RequestTask task = new RequestTask(request);
        task.execute();
    }

    public void testHttpPostOnSubThreadForGeneric() {
        String url = "http://api.stay4it.com/v1/public/core/?service=user.login";
        String content = "account=stay4it&password=123456";
        Request request = new Request(url, Request.RequestMethod.POST);
        request.setCallback(new JsonCallback<User>() {

            @Override
            public void onSuccess(User result) {
                Log.e("stay", "testHttpGet return:" + result.toString());
            }

            @Override
            public void onFailure(AppException e) {
                e.printStackTrace();
            }


        });
        request.content = content;
        request.maxRetryCount=0;
        RequestTask task = new RequestTask(request);
        task.execute();
        task.cancel(true);
        request.cancel();
    }

    public void testHttpPostOnSubThreadForDownload() {
        String url = "http://api.stay4it.com/v1/public/core/?service=user.login";
        String content = "account=stay4it&password=123456";
        Request request = new Request(url, Request.RequestMethod.POST);
        String path = Environment.getExternalStorageDirectory() + File.separator + "filedownload.txt";
        request.setCallback(new FileCallback() {
            @Override
            public void onSuccess(String Result) {
                Log.e("ysy", Result);
            }

            @Override
            public void onFailure(AppException e) {

            }
        }.setCachePath(path));
        request.content = content;
        RequestTask task = new RequestTask(request);
        task.execute();
    }

    public void testHttpPostOnSubThreadForDownloadProgress() {
        String url = "http://api.stay4it.com/uploads/test.jpg";
        Request request = new Request(url, Request.RequestMethod.GET);
        String path = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
        request.setCallback(new FileCallback() {

            @Override
            public void onProgressUpdated(int curLen, int totalLen) {
                Log.e("ysy", "download:" + curLen + "/" + totalLen);
            }

            @Override
            public void onSuccess(String Result) {
                Log.e("ysy", Result);
            }

            @Override
            public void onFailure(AppException e) {

            }
        }.setCachePath(path));
        request.enableProgressUpdated(true);
        RequestTask task = new RequestTask(request);
        task.execute();
    }

    public void testHttpPostOnSubThreadForDownloadProgressCancelTest() {

        String url = "http://api.stay4it.com/uploads/test.jpg";
        final  Request request = new Request(url, Request.RequestMethod.GET);
        final RequestTask task = new RequestTask(request);
        String path = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
        request.setCallback(new FileCallback() {
            @Override
            public void onProgressUpdated(int curLen, int totalLen) {
                Log.e("stay", "download:" + curLen + "/" + totalLen);
                if(curLen * 100l / totalLen > 50){
//                    task.cancel(true);
                    request.cancel();
                }
            }


            @Override
            public void onSuccess(String path) {
                Log.e("stay", path);
            }

            @Override
            public void onFailure(AppException e) {
                if (e.type == AppException.ErrorType.CANCEL){

                }
                e.printStackTrace();
            }
        }.setCachePath(path));
        request.enableProgressUpdated(true);

        task.execute();
    }
}
