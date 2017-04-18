package com.yao.ota.app.feed.model;

/**
 * 加载更多的info
 * Created by huichuan on 2017/4/18.
 */
public class LoadMoreInfo extends OtaInfo {

    private String loadMoreText;

    public LoadMoreInfo(String text){
        loadMoreText = text;
    }

    public String getLoadMoreText() {
        return loadMoreText;
    }
}
