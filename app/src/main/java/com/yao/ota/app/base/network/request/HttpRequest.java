package com.yao.ota.app.base.network.request;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.ota.app.DroidApplication;
import com.yao.ota.app.base.network.BaseRequest;
import com.yao.ota.app.base.network.manager.RequestManager;
import com.yao.ota.app.base.network.utils.NetParamsUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求的基类
 * Created by huichuan on 16/10/10.
 */
public abstract class HttpRequest {
    private static final String TAG = "HttpRequest";


    /**
     * 请求结果
     * @param <T>
     */
    public interface RequestCallback<T>{
        void onResponse(T response);
        void onErrorResponse(Exception error);
    }

    /**
     * 记录请求创建时客户端登录状态
     */
    public static final String ARGS_IS_LOGIN = "hl_is_login";

    Context appContext = DroidApplication.getApplication();

    //请求的方法,默认是get （访问权限仅限于包内）
    private int mRequestMethod = Request.Method.GET;
    private String mRequestUrl;
    private String mRequestPath;

    /**
     * 此二参数为必传参数
     *
     * @param method
     * @param urlOrPath
     */
    protected HttpRequest(int method, String urlOrPath){
        mRequestMethod = method;
        if (urlOrPath.startsWith("http")){
            //此为url
            mRequestUrl = urlOrPath;
        }else{
            //此为requestPath,需要本地拼接
            mRequestPath = urlOrPath;
        }
    }

    /**
     * 供子类重写的方法
     * 有额外公参重写此方法
     *
     * @return
     */
    protected Map<String, String> getExtraParams() {
        if (isRequestPost()) {
            //post request会调用getExtraParams方法
            //方便子类调用，返回一个空hashMap
            return new HashMap<>();
        } else if (isRequestGet()) {
            //get 请求，将公参返回
            return BaseRequest.getPublicParams();
        } else {
            throw new IllegalStateException(mRequestPath + " 使用了不可识别的Http请求类型");
        }

    }

    /**
     * 是否是get请求
     *
     * @return
     */
    boolean isRequestGet() {
        return (mRequestMethod == Request.Method.GET);
    }

    /**
     * 是否是Post请求
     *
     * @return
     */
    boolean isRequestPost() {
        return (mRequestMethod == Request.Method.POST);
    }

    /**
     * 根据请求类型创建一个对应的请求
     *
     * @return
     */
    JsonObjectRequest createRequest(RequestCallback<JSONObject> requestCallback){


        JsonObjectRequest request = null;
        //创建内部的请求成功失败listener
        InnerSuccessListener successListener = new InnerSuccessListener(requestCallback);
        InnerErrorListener errorListener = new InnerErrorListener(requestCallback);

        if (mRequestMethod == Request.Method.GET) {
            //GET 请求，则在url上拼接公参
            String requestUrl;
            if (!TextUtils.isEmpty(mRequestUrl)){
                //完整的url请求
                requestUrl = mRequestUrl;
            }else{
                requestUrl = NetParamsUtils.assemblePathWithParams(mRequestPath, getExtraParams());
            }

            LoggerUtil.i(TAG,"get请求的url:"+requestUrl);
            request = new JsonObjectRequest(Request.Method.GET,requestUrl, successListener, errorListener);
        }else if (mRequestMethod == Request.Method.POST){

            String requestUrl = (!TextUtils.isEmpty(mRequestUrl))?mRequestUrl:NetParamsUtils.getToggleUrl(mRequestPath);
            LoggerUtil.i(TAG,"post请求的url:"+requestUrl);
            request = new JsonObjectRequest(Request.Method.POST,requestUrl, successListener, errorListener){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return getExtraParams();
                }
            };
        }

        return request;
    }

    /**
     * 创建InnerSuccessListener 是为了允许请求可以不传Listener
     */
    private class InnerSuccessListener implements Response.Listener<JSONObject> {

        RequestCallback<JSONObject> mRequestCallback;

        InnerSuccessListener(RequestCallback<JSONObject> mRequestCallback) {
            this.mRequestCallback = mRequestCallback;
        }

        @Override
        public void onResponse(JSONObject response) {
            if (mRequestCallback != null) {
                mRequestCallback.onResponse(response);
            }
        }
    }

    /**
     * 创建InnerErrorListener 是为了允许请求可以不传Listener
     */
    private class InnerErrorListener implements Response.ErrorListener {
        RequestCallback<JSONObject> mRequestCallback;

        InnerErrorListener(RequestCallback<JSONObject> mRequestCallback) {
            this.mRequestCallback = mRequestCallback;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            if (mRequestCallback != null) {
                mRequestCallback.onErrorResponse(error);
            }
        }
    }

    /**
     * 内部调用真正的请求服务器
     */
    protected abstract void innerRequestServer(String tag);

    public void addToRequestQueue() {
        addToRequestQueue(TAG, false);
    }

    /**
     * 给当前request添加一个TAG,一般用当前Activity或者Fragment的名字，即TAG
     */
    public void addToRequestQueue(String tag) {
        addToRequestQueue(tag, false);
    }

    /**
     * 给当前request添加一个TAG,一般用当前Activity或者Fragment的名字，即TAG
     */
    public void addToRequestQueue(String tag, boolean checkNetWork) {

        innerRequestServer(tag);
    }

    /**
     * 根据在{@link HttpRequest#addToRequestQueue(String)}中设置的tag取消对应请求.
     *
     * @param tag
     */
    public static void cancelPendingRequests(String tag) {
        RequestManager.getInstance()
                      .cancelPendingRequests(tag);
    }

}
