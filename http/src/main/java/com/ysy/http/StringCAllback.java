package com.ysy.http;

/**
 * Created by yeshiyuan on 2015/9/7.
 */
public abstract class StringCAllback extends AbstractCallback<String> {
    @Override
    protected String bindData(String result) throws Exception {
        return result;
    }
}
