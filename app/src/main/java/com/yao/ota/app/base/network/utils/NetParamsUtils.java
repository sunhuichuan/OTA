package com.yao.ota.app.base.network.utils;

import com.yao.devsdk.log.LogUtil;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Class
 * Created by huichuan on 2017/1/7.
 */

public class NetParamsUtils {
    private static final String TAG = "NetParamsUtils";


    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private static final String HOST = "host";


    public static String getToggleUrl(String path) {

        return HOST+path;

    }
    public static String assemblePathWithParams(String path, Map<String,String>... params) {

        return assembleParams(getToggleUrl(path),params);

    }

    /**
     * 拼装参数到URL后面
     * @param url
     * @param params
     * @return
     */
    public static String assembleParams(String url, Map<String,String>... params) {
        String assmbleUrl = url;
        try {
            Map<String,String> getParams = new HashMap<>();
            for (int i=0;i<params.length;i++){
                if (params[i] == null){
                    continue;
                }
                getParams.putAll(params[i]);
            }

            if (getParams.size() > 0) {
                StringBuilder encodedParams = new StringBuilder();

                String paramsEncoding = DEFAULT_PARAMS_ENCODING;

                for (Map.Entry<String, String> entry : getParams.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                    encodedParams.append('&');
                }
                if (url.contains("?")){
                    //带？代表此url含有参数
                    if (url.endsWith("&")){
                        assmbleUrl = url + encodedParams.toString();
                    }else{
                        assmbleUrl = url + "&" + encodedParams.toString();
                    }
                }else{
                    assmbleUrl = url + "?" + encodedParams.toString();
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "拼接URL异常", e);
        }

        return assmbleUrl;
    }
}
