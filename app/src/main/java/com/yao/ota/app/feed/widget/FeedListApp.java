package com.yao.ota.app.feed.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.yao.dependence.widget.feedlist.FeedListBase;
import com.yao.dependence.widget.feedlist.FeedRecyclerView;
import com.yao.devsdk.utils.DisplayUtil;
import com.yao.ota.app.feed.model.OtaInfo;
import com.yao.ota.app.feed.requestcallback.FeedRequestCallback;

/**
 * 展示app列表的FeedList
 * Created by huichuan on 2017/6/2.
 */
public class FeedListApp extends FeedRecyclerView<OtaInfo> {
    public FeedListApp(Context context) {
        super(context);
    }

    public FeedListApp(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FeedListApp(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setRecyclerViewLayoutManager() {
//        super.setRecyclerViewLayoutManager();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        //增加每个CardView的边距
        SpacesItemDecoration decoration=new SpacesItemDecoration(DisplayUtil.dp2px(6));
        mRecyclerView.addItemDecoration(decoration);
    }

    /**
     * 设置发起网络请求的回调
     */
    @Override
    public FeedListBase setCallbacks(FeedListCallbacks callbacks) {
        ((FeedRequestCallback)callbacks).setFeedRecyclerView(this);
        return super.setCallbacks(callbacks);

    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpacesItemDecoration(int space) {
            this.space=space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }


}
