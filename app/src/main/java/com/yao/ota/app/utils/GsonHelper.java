package com.yao.ota.app.utils;

import com.google.gson.Gson;
import com.yao.devsdk.log.LoggerUtil;

import java.lang.reflect.Type;

public class GsonHelper {
    private static final String TAG = "GsonHelper";

    private static Gson INSTANCE;

    static {
        INSTANCE = new Gson();
    }

    public static <T> T parse(String strDataJson, Class<T> classOfT) {
        T data = null;
        if (null != strDataJson) {
            try {
                data = INSTANCE.fromJson(strDataJson, classOfT);
            } catch (Exception e) {
                LoggerUtil.e(TAG,"Exception:",e);
            }
        }
        return data;
    }
    public static <T> T parse(String strDataJson, Type typeOfT) {
        T data = null;
        if (null != strDataJson) {
            try {
                data = INSTANCE.fromJson(strDataJson, typeOfT);
            } catch (Exception e) {
                LoggerUtil.e(TAG,"Exception:",e);
            }
        }
        return data;
    }

    public static <T> T parse(byte[] dataJson, Class<T> classOfT) {
        T data = null;
        String strDataJson = null;
        try {
            strDataJson = new String(dataJson, "utf-8");
            data = parse(strDataJson, classOfT);
        } catch (Exception e) {
            LoggerUtil.e(TAG,"Exception:",e);
        }
        return data;
    }
}
