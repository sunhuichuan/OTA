package com.yao.ota.app.feed.viewholder;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yao.devsdk.log.LoggerUtil;
import com.yao.ota.R;
import com.yao.ota.app.feed.model.OtaAppInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * App的ViewHolder
 */
public class AppCardViewHolder extends FeedViewHolder {
    private static final String TAG = "AppCardViewHolder";
    public View item;
    public TextView tv_app_version;
    public TextView tv_app_description;
    public TextView tv_app_publish_time;
    public ImageView iv_new_app_mark;


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
        tv_app_publish_time = (TextView) view.findViewById(R.id.tv_app_publish_time);
        iv_new_app_mark = (ImageView) view.findViewById(R.id.iv_new_app_mark);
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
        tv_app_publish_time.setText("发布时间："+appInfo.getPublishTime());
        if (isPublishTimeOver24Hour(appInfo)){
            iv_new_app_mark.setVisibility(View.INVISIBLE);
            tv_app_version.setTextColor(Color.parseColor("#7A67EE"));
        }else{
            iv_new_app_mark.setVisibility(View.VISIBLE);
            tv_app_version.setTextColor(Color.RED);
        }
    }


    /**
     * 是否是24小时内发布的app
     * @return
     */
    private boolean isPublishTimeOver24Hour(OtaAppInfo appInfo){
        try {
            String publishTime = appInfo.getPublishTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date publishDate = sdf.parse(publishTime);
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            long time = publishDate.getTime();
            long space = timeInMillis - time;
            if (space > 24*60*60*1000){
                //24小时前发布的
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            LoggerUtil.e(TAG,"异常");
            return true;
        }
    }
}