package com.yao.ota.app.base.network.request;

import com.android.volley.toolbox.JsonObjectRequest;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.ota.app.base.network.manager.RequestManager;
import com.yao.ota.app.base.network.toolbox.SimpleRequestCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有只需要返回JSON的http请求,可以继承此抽象Request
    */
    public class HttpJSONRequest extends HttpRequest{
        private static final String TAG = "HttpJSONRequest";

    private final RequestCallback<JSONObject> mListener;
    //构造时传进来的额外参数
    private final Map<String,String> mExtraParams = new HashMap<>();


    /**
     * 没有额外请求参数的构造方法
     * @param urlOrPath 完整url或者path
     */
    public HttpJSONRequest(int method, String urlOrPath,
                           RequestCallback<JSONObject>  listener) {
        this(method,urlOrPath,null,listener);
    }
    /**
     * 需要返回json响应的构造
     * @param method
     * @param urlOrPath 完整url或者path
     * @param listener
     */
    public HttpJSONRequest(int method, String urlOrPath, Map<String,String> extraParams,
                           RequestCallback<JSONObject> listener) {
        super(method,urlOrPath);
        mListener = listener;
        //额外请求参数
        if (extraParams!=null){
            mExtraParams.putAll(extraParams);
        }

    }

    @Override
    protected Map<String, String> getExtraParams() {
        Map<String,String> params = super.getExtraParams();
        params.putAll(mExtraParams);
        return params;
    }

    /**
     * 内部调用真正的请求服务器
     */
    @Override
    protected void innerRequestServer(String tag){
        JsonObjectRequest request = createRequest(new SuccessListener());
        RequestManager.getInstance().addToRequestQueue(request,tag);
    }


    /**
     * 响应成功的回调
     */
    class SuccessListener extends SimpleRequestCallback<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {
            LoggerUtil.i(TAG,"收到的响应结果："+response.toString());
            if (null != mListener) {
                mListener.onResponse(response);
            }
        }
    }


}


