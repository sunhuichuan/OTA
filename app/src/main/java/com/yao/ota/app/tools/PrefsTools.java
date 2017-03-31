package com.yao.ota.app.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.yao.devsdk.prefs.base.CachedPrefs;
import com.yao.ota.app.DroidApplication;

/**
 * 工具设置的开关配置
 * Created by huichuan on 2017/2/22.
 */
public class PrefsTools extends CachedPrefs {

    private PrefsTools(SharedPreferences pref) {
        super(pref);
    }

    private static PrefsTools prefsTools;

    public static PrefsTools getInstance(){
        if (prefsTools == null){
            SharedPreferences toolsSetting = DroidApplication.getApplication().getSharedPreferences("toolsSetting", Context.MODE_APPEND);
            prefsTools = new PrefsTools(toolsSetting);
        }
        return prefsTools;
    }


    /**
     * 是否屏蔽视频的广告
     */
    public BoolVal is_forbid_video_ad = new BoolVal("is_forbid_video_ad",false);




}
