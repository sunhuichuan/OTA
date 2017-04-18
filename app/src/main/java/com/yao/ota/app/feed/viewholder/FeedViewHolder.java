package com.yao.ota.app.feed.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yao.ota.app.DroidApplication;
import com.yao.ota.app.feed.model.OtaInfo;

/**
 * Feed流中需要的ViewHolder
 */
public abstract class FeedViewHolder extends RecyclerView.ViewHolder {
    protected Context appContext = DroidApplication.getApplication();
    public View item;
    private OtaInfo otaInfo;

    protected FeedViewHolder(View view) {
        super(view);
        item = view;
    }

    public void setOtaInfo(OtaInfo otaInfo) {
        this.otaInfo = otaInfo;
    }

    public OtaInfo getOtaInfo() {
        return otaInfo;
    }

    public abstract void update();

}