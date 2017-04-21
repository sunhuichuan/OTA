package com.yao.ota.app.feed.model;

/**
 * Class
 * Created by huichuan on 2017/4/18.
 */
public class OtaInfo {

    public static final int INFO_TYPE_APP_CATEGORY = 0;
    /**
     * App信息对象
     */
    public static final int INFO_TYPE_APP_ITEM = 1;
    /**
     * 加载更多对象
     */
    public static final int INFO_TYPE_LOAD_MORE = 2;


    /**
     * info类型
     * {@link #INFO_TYPE_APP_ITEM}
     */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public boolean isAppInfo(){
        return type == INFO_TYPE_APP_ITEM;
    }
}
