package com.yao.ota.app.feed.model;

/**
 * Ota的app实体bean
 * Created by huichuan on 2017/4/12.
 */
public class OtaAppInfo extends OtaInfo{

    //app的发布时间
    private String publishTime;
    //app的versinoName
    private String appVersionName;
    //app下载的url
    private String appDownloadUrl;
    //app的描述
    private String appDescription;
    //删除app需要调用的url
    private String deleteActionUrl;

    public OtaAppInfo(){
        //app类型，设置type值
        setType(INFO_TYPE_APP_ITEM);
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getAppVersionName(){
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getDeleteActionUrl() {
        return deleteActionUrl;
    }

    public void setDeleteActionUrl(String deleteActionUrl) {
        this.deleteActionUrl = deleteActionUrl;
    }




}
