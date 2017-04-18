package com.yao.ota.app.feed.viewholder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yao.ota.R;
import com.yao.ota.app.feed.model.OtaAppInfo;

/**
 * App的ViewHolder
 */
public class AppCardViewHolder extends FeedViewHolder {
    public View item;
    public TextView tv_app_version;
    public TextView tv_app_description;


    public static AppCardViewHolder getInstance(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_app, parent, false);
        AppCardViewHolder vh = new AppCardViewHolder(v);
        return vh;
    }

    private AppCardViewHolder(View view) {
        super(view);
        item = view;
        tv_app_version = (TextView) view.findViewById(R.id.tv_app_version);
        tv_app_description = (TextView) view.findViewById(R.id.tv_app_description);
    }


    @Override
    public OtaAppInfo getOtaInfo() {
        return (OtaAppInfo) super.getOtaInfo();
    }

    @Override
    public void update() {
        OtaAppInfo appInfo = getOtaInfo();
        tv_app_version.setText(appInfo.getAppVersionName());
        String appDescription = appInfo.getAppDescription();
        if (TextUtils.isEmpty(appDescription)){
            appDescription = "缺少描述";
        }
        tv_app_description.setText(appDescription);
    }
}