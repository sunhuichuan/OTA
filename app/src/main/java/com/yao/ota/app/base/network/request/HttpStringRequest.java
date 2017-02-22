package com.yao.ota.app.base.network.request;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yao.devsdk.log.LogUtil;
import com.yao.ota.app.base.network.manager.RequestManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有只需要返回String的http请求,可以继承或直接调用此Request
 */
public class HttpStringRequest extends HttpRequest {
    private static final String TAG = "HttpStringRequest";

    private String mRequestUrl;
    private final RequestCallback<String> mListener;
    //构造时传进来的额外参数
    private final Map<String, String> mExtraParams = new HashMap<>();


    /**
     * 没有额外请求参数的构造方法
     *
     * @param urlOrPath 完整url或者path
     */
    public HttpStringRequest(int method, String urlOrPath,
                             RequestCallback<String> listener) {
        this(method, urlOrPath, null, listener);
    }

    /**
     * 需要返回json响应的构造
     *
     * @param method
     * @param urlOrPath     完整url或者path
     * @param listener
     */
    public HttpStringRequest(int method, String urlOrPath, Map<String, String> extraParams,
                             RequestCallback<String> listener) {
        super(method, urlOrPath);
        mRequestUrl = urlOrPath;
        mListener = listener;
        //额外请求参数
        if (extraParams != null) {
            mExtraParams.putAll(extraParams);
        }

    }

    @Override
    protected Map<String, String> getExtraParams() {
        Map<String, String> params = super.getExtraParams();
        params.putAll(mExtraParams);
        return params;
    }

    /**
     * 内部调用真正的请求服务器
     */
    @Override
    protected void innerRequestServer(String tag) {

        StringRequest stringRequest = new StringRequest(mRequestUrl, new SuccessListener(), new ErrorListener());

        RequestManager.getInstance().addToRequestQueue(stringRequest, tag);
    }


    /**
     * 响应成功的回调
     */
    class SuccessListener implements Listener<String> {

        @Override
        public void onResponse(String response) {
            LogUtil.i(TAG, "收到的响应结果：" + response);
            if (null != mListener) {
                mListener.onResponse(response);
            }
        }
    }
    /**
     * 响应成功的回调
     */
    private class ErrorListener implements Response.ErrorListener {


        @Override
        public void onErrorResponse(VolleyError error) {
            LogUtil.i(TAG, "收到结果错误：" + error);
            if (null != mListener) {
                mListener.onErrorResponse(error);
            }
        }
    }


}


