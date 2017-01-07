package com.yao.ota;

import android.app.Application;
import android.content.Context;

/**
 * F
 * Created by huichuan on 2017/1/7.
 */

public class DroidApplication extends Application {


    private static Context appContext;


    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }






}
