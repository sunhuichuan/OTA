package com.yao.ota.app.feed.model;

/**
 * Ota的app类别信息
 * Created by huichuan on 2017/4/12.
 */
public class OtaAppCategory extends OtaInfo{

    //app的包名
    private String packageName;
    //app的iconUrl
    private String logoUrl;
    //app名字
    private String appName;

    public OtaAppCategory(){
        //app类型，设置type值
        setType(INFO_TYPE_APP_CATEGORY);
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
