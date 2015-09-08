package com.ysy.http;

/**
 * Created by yeshiyuan on 2015/9/7.
 */
public  abstract class FileCallback extends AbstractCallback<String>{
    @Override
    protected String bindData(String path) throws AppException {
        return path;
    }
}
