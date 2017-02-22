package com.yao.ota.app.base.network.toolbox;

import com.yao.devsdk.log.LogUtil;
import com.yao.ota.app.base.network.request.HttpRequest;

/**
 * 简单的请求callback
 * Created by huichuan on 2017/2/22.
 */
public class SimpleRequestCallback<T> implements HttpRequest.RequestCallback<T> {

    private static final String TAG = "SimpleRequestCallback";


    @Override
    public void onResponse(T response) {
        LogUtil.i(TAG,"请求成功:"+(response==null?"--空--":response));
    }

    @Override
    public void onErrorResponse(Exception exception) {
        LogUtil.i(TAG,"请求异常",exception);
    }
}
