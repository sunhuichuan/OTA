package com.yao.ota.app;

import android.content.Context;

import com.yao.devsdk.SdkConfig;
import com.yao.devsdk.utils.DisplayUtil;

public class BaseConfig extends SdkConfig {
    private static final String TAG = "BaseConfig";

    private static Context mContext;

    /**
     * 小图的宽高系数
     */
    public static final float THUMB_PIC_RATIO = ((float) 3) / 2;
    /**
     * 大图的宽高系数
     */
    public static final float BIG_PIC_RATIO = ((float) 16) / 9;


    //feed中显示的缩略图宽高
    public static int ThumbWidth;
    public static int ThumbHeight;

    //feed中显示的大图宽高
    public static int BigPicWidth;
    public static int BigPicHeight;




    public static void initBaseConfig(Context context) {
        mContext = context;
        initDeviceInfo(context);
        initUidToken();

    }


    /**
     * 获取设备硬件相关信息
     *
     * @param context
     */
    private static void initDeviceInfo(Context context) {
        initPicWidthHeight();
    }


    private static void initPicWidthHeight() {
        int screenWidth = SdkConfig.mScreenWidth;
        ThumbWidth = (screenWidth - DisplayUtil.dip2px(mContext, 36)) / 3;
        ThumbHeight = (int) (ThumbWidth / THUMB_PIC_RATIO);
        BigPicWidth = screenWidth - DisplayUtil.dip2px(mContext, 24);
        BigPicHeight = (int) (BigPicWidth / BIG_PIC_RATIO);
    }




}
