package com.yao.ota.app.base;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.yao.ota.app.base.network.manager.RequestManager;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class BaseRequest<T> extends Request<T> {
    private static final String TAG = "BaseRequest";


    public RequestManager mRequestManager = RequestManager.getInstance();
    protected String mRequestUrl;
    //post的参数
    private Map<String, String> mPostParams = new HashMap<>();



    public BaseRequest(int method, String url, ErrorListener listener) {
        super(method, url, listener);
        mRequestUrl = url;
    }

    /**
     * 给新请求格式增加的构造方法
     * @param method
     * @param url
     * @param postParams
     * @param listener
     */
    public BaseRequest(int method, String url, Map<String, String> postParams, ErrorListener listener) {
        super(method, url, listener);
        mRequestUrl = url;
        if (postParams!=null){
            mPostParams.putAll(postParams);
        }
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = getPublicParams();
        params.putAll(mPostParams);
        return params;
    }


    /**
     * 获取公共参数Map
     * @return
     */
    public static Map<String,String> getPublicParams(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("vt", "3");
        return params;
    }

    /**
     * 避免param为空
     */
    static String addParam(String param){
        if (param == null){
            return "";
        }else{
            return param;
        }
    }


    Map<String, String> headers = new HashMap<>();
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        //创建一个存header的HashMap,供httpDNS增加参数使用
        return headers;
    }






    public void addToRequestQueue() {
        mRequestManager.addToRequestQueue(this);
    }

    /**
     * 给当前request添加一个TAG,一般用当前Activity或者Fragment的名字，即TAG
     */
    public void addToRequestQueue(String tag) {
        mRequestManager.addToRequestQueue(this, tag);
    }

    public static <K,V> Map<K,V> combineParams(Map<K,V> map1, Map<K,V> map2) {
        if (map1 == null) {
            map1 = new HashMap<>();
        }
        if (map2 == null) {
            map2 = new HashMap<>();
        }
        map1.putAll(map2);
        return map1;
    }

}
