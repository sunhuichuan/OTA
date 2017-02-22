package com.yao.ota.app.base.network.request;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yao.devsdk.log.LogUtil;
import com.yao.ota.app.base.network.manager.RequestManager;
import com.yao.ota.app.base.network.toolbox.SimpleRequestCallback;
import com.yao.ota.app.constant.XConstants;
import com.yao.ota.app.utils.GsonHelper;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 所有需要GSON解析的Http请求,可以继承此抽象Request
 */
public abstract class HttpGsonRequest<T> extends HttpRequest {
    private static final String TAG = "HttpGsonRequest";

    private final RequestCallback<T> mListener;

    /**
     * http请求的构造方法
     * @param method {@link com.android.volley.Request.Method#GET,com.android.volley.Request.Method#POST}
     * @param urlOrPath
     * @param listener
     */
    public HttpGsonRequest(int method,String urlOrPath,
                      RequestCallback<T> listener) {
        super(method,urlOrPath);
        mListener = listener;
    }


    /**
     * 内部调用真正的请求服务器
     */
    @Override
    protected void innerRequestServer(String tag) {
        final Type mType = getType(this, HttpGsonRequest.class);
        JsonObjectRequest request = createRequest(new SuccessListener(mType));
        RequestManager.getInstance().addToRequestQueue(request,tag);
    }

    /**
     * 获取直接子类的泛型参数
     *
     * @return
     */
    public static Type getType(Object obj, Class superclass) {
        Type type = obj.getClass().getGenericSuperclass();
        while ((type instanceof Class) && !type.equals(superclass)) {
            type = ((Class) type).getGenericSuperclass();
        }
        if (type instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) type;
        return parameterized.getActualTypeArguments()[0];
    }

    /**
     * 请求成功的listener
     */
    class SuccessListener extends SimpleRequestCallback<JSONObject> {

        final Type mType;

        public SuccessListener(Type type) {
            mType = type;
        }

        @Override
        public void onResponse(JSONObject response) {
            if (XConstants.DEBUG) {
                //HttpGetRequest$1 个什么东西？哪个内部类？应该是Listener<JSONObject>
                LogUtil.i(TAG, "this是：" + this.getClass());
            }
            if (mListener != null) {
                T result = GsonHelper.parse(response.toString(), mType);
                if (null != result) {
                    mListener.onResponse(result);
                } else {
                    onErrorResponse(new VolleyError("数据为空"));
                }
            }
        }
    }

}


