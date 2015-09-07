package com.ysy.ysy_http;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ysy.http.FileCallback;
import com.ysy.http.JsonCallback;
import com.ysy.http.Request;
import com.ysy.http.RequestTask;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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
                testHttpPostOnSubThreadForDownload();
                break;
        }
    }

    private void testHttpPostOnSubThread() {
        String url = "http://api.stay4it.com/v1/public/core/?service=user.login";
        String content = "account=stay4it&password=123456";
        Request request = new Request(url, Request.RequestMethod.POST);

        request.setCallback(new JsonCallback<String>() {

            @Override
            public void onSucess(String Result) {
                Log.e("stay", "testHttpGet return:" + Result);
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
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
            public void onSucess(User result) {
                Log.e("stay", "testHttpGet return:" + result.toString());
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }


        });
        request.content = content;
        RequestTask task = new RequestTask(request);
        task.execute();
    }

    public void testHttpPostOnSubThreadForDownload() {
        String url = "http://api.stay4it.com/v1/public/core/?service=user.login";
        String content = "account=stay4it&password=123456";
        Request request = new Request(url, Request.RequestMethod.POST);
        String path = Environment.getExternalStorageDirectory() + File.separator + "filedownload.txt";
        request.setCallback(new FileCallback() {
            @Override
            public void onSucess(String Result) {
                Log.e("ysy", Result);
            }

            @Override
            public void onFailure(Exception e) {

            }
        }.setCachePath(path));
        request.content = content;
        RequestTask task = new RequestTask(request);
        task.execute();
    }
}
