package com.yao.ota.app.feed.adapter;

import android.view.ViewGroup;

import com.yao.devsdk.adapter.AceRecyclerAdapter;
import com.yao.ota.app.feed.model.OtaInfo;
import com.yao.ota.app.feed.viewholder.AppCardViewHolder;
import com.yao.ota.app.feed.viewholder.FeedViewHolder;
import com.yao.ota.app.feed.viewholder.LoadMoreViewHolder;


/**
 * app信息展示的adapter
 */
public class AppInfoListAdapter extends AceRecyclerAdapter<OtaInfo,FeedViewHolder>{


    public AppInfoListAdapter() {
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if (viewType == OtaInfo.INFO_TYPE_APP){
            return AppCardViewHolder.getInstance(parent);
        }else{
            return LoadMoreViewHolder.getInstance(parent);
        }

    }


    @Override
    public void onInnerBindViewHolder(FeedViewHolder viewHolder, OtaInfo item, int position) {
        viewHolder.item.setTag(position);
        viewHolder.setOtaInfo(item);
        viewHolder.update();
    }

    @Override
    public int getItemCount()
    {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == (getItemCount()-1)){
            return OtaInfo.INFO_TYPE_LOAD_MORE;
        }else{
            return OtaInfo.INFO_TYPE_APP;
        }
    }
}
