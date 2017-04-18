package com.yao.ota.app.feed.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yao.devsdk.adapter.AceRecyclerAdapter;
import com.yao.ota.R;
import com.yao.ota.app.feed.model.OtaAppInfo;


/**
 * app信息展示的adapter
 */
public class AppInfoListAdapter extends AceRecyclerAdapter<OtaAppInfo,AppInfoListAdapter.ViewHolder>{


    public AppInfoListAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_app,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onInnerBindViewHolder(ViewHolder viewHolder, OtaAppInfo item, int position) {
        viewHolder.item.setTag(position);
        viewHolder.tv_app_version.setText(getList().get(position).getAppVersionName());
        viewHolder.tv_app_description.setText(getList().get(position).getAppDescription());
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public View item;
        public TextView tv_app_version;
        public TextView tv_app_description;
        public ViewHolder(View view){
            super(view);
            item = view;
            tv_app_version = (TextView) view.findViewById(R.id.tv_app_version);
            tv_app_description = (TextView) view.findViewById(R.id.tv_app_description);
        }
    }
    @Override
    public int getItemCount()
    {
        return getCount();
    }

    public void addItem(OtaAppInfo s, int position) {
        add(position,s);
        notifyItemInserted(position);
    }

    public void removeItem(final int position) {
        remove(position);
        notifyItemRemoved(position);
    }
}
