package com.ysy.ysy_http;

import android.app.Activity;

import com.ysy.http.AppException;
import com.ysy.http.OnGlobalExceptionListener;

/**
 * User: ysy
 * Date: 2015/9/10
 * Time: 8:30
 */
public class BaseActivity extends Activity implements OnGlobalExceptionListener {
    @Override
    public boolean handleException(AppException e) {
        if (e.statusCode == 403) {
            if ("token invalid".equals(e.responseMessage)) {
//                        TODO relogin
                return true;
            }
        }
        return false;
    }
}
