package com.yao.ota.app.feed.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yao.devsdk.utils.DisplayUtil;
import com.yao.ota.R;
import com.yao.ota.app.feed.model.LoadMoreInfo;
import com.yao.ota.app.feed.model.OtaInfo;

/**
 * 加载更多的ViewHolder
 */
public class LoadMoreViewHolder extends FeedViewHolder {
    public LinearLayout item;
    public TextView tv_loadMore;


    public static LoadMoreViewHolder getInstance(ViewGroup parent) {
        Context context = parent.getContext();
        LinearLayout linearLayout = new LinearLayout(context);

        LoadMoreViewHolder vh = new LoadMoreViewHolder(linearLayout);
        return vh;
    }

    private LoadMoreViewHolder(LinearLayout view) {
        super(view);
        item = view;
        int padding = DisplayUtil.dip2px(appContext, 15);
        item.setPadding(padding,padding,padding,padding);
        item.setGravity(Gravity.CENTER);
        tv_loadMore = new TextView(view.getContext());
        item.addView(tv_loadMore);
    }

    @Override
    public LoadMoreInfo getOtaInfo() {
        return (LoadMoreInfo) super.getOtaInfo();
    }

    @Override
    public void update() {
        LoadMoreInfo otaInfo = getOtaInfo();
        tv_loadMore.setText(otaInfo.getLoadMoreText());
    }
}