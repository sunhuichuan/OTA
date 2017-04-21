package com.yao.ota.app.feed.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yao.dependence.ui.BaseFragment;
import com.yao.dependence.widget.recycler.listener.OnRcvScrollListener;
import com.yao.devsdk.adapter.AceRecyclerAdapter;
import com.yao.ota.R;
import com.yao.ota.app.feed.adapter.AppInfoListAdapter;
import com.yao.ota.app.feed.controller.FeedContainerController;
import com.yao.ota.app.feed.model.LoadMoreInfo;
import com.yao.ota.app.feed.model.OtaAppInfo;
import com.yao.ota.app.feed.model.OtaInfo;
import com.yao.ota.app.feed.mvp.AppListContract;
import com.yao.ota.app.feed.mvp.AppListPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * OTA上App的列表
 */
public class FragmentOtaApp extends BaseFragment implements AppListContract.View {
    private static final String TAG = "FragmentOtaApp";

    public static final String ARGS_KEY_TYPE_NAME = "app_type_name";

    @Bind(R.id.rv_recyclerView)
    RecyclerView recyclerView;

    private String appPackageName;
    private String appTypeName;

    private FeedContainerController.AppPagerAdapter appPagerAdapter;
    private AppInfoListAdapter adapter;
    private AppListContract.Presenter appListPresenter;


    public static FragmentOtaApp newInstance(FeedContainerController.AppPagerAdapter appPagerAdapter, String typeName){
        FragmentOtaApp fragmentOtaApp = new FragmentOtaApp();
        fragmentOtaApp.appPagerAdapter = appPagerAdapter;
        Bundle argsBundle = new Bundle();
        argsBundle.putString(ARGS_KEY_TYPE_NAME,typeName);
        fragmentOtaApp.setArguments(argsBundle);
        return fragmentOtaApp;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_app, container, false);
        ButterKnife.bind(this, view);
        appListPresenter = new AppListPresenter(this);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new AppInfoListAdapter();
        recyclerView.setAdapter(adapter);

        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

        adapter.setOnItemClickListener(new AceRecyclerAdapter.OnItemClickListener<OtaInfo>() {
            @Override
            public void onItemClick(View view, int position, OtaInfo item) {
                if (item.isAppInfo()){
                    OtaAppInfo appInfo = (OtaAppInfo) item;
                    Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(appInfo.getAppDownloadUrl()));
                    thisContext.startActivity(it);
                }else{
//                    Snackbar.make(view, "Click to load more "+view.getTag(), Snackbar.LENGTH_LONG).show();
                    appListPresenter.loadMore();
                }
            }
        });

        recyclerView.addOnScrollListener(new OnRcvScrollListener(){
            @Override
            public void onBottom() {
                //滚动到了底部
            }
        });

        appListPresenter.start();
        return view;
    }

    @Override
    public void setPresenter(AppListContract.Presenter presenter) {
    }

    @Override
    public String getAppPackageName() {
        return appPagerAdapter.getPackageName();
    }

    @Override
    public String getAppTypeName() {
        Bundle arguments = getArguments();
        if (arguments!=null){
            appTypeName = arguments.getString(ARGS_KEY_TYPE_NAME);
        }
        return appTypeName;
    }

    @Override
    public void showSnackToast(String toastText) {
        //显示toast
        Snackbar.make(recyclerView, toastText, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void setAppInfoList(List<OtaInfo> appInfoList) {
        appInfoList.add(new LoadMoreInfo("点击加载更多"));
        adapter.setList(appInfoList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addAppInfoListToAdapter(List<OtaInfo> appInfoList) {
        //把最后一个加载更多条目去掉
        int count = adapter.getCount();
        if (count>1){
            //除了加载更多，还有其他数据
            adapter.addAll(count-1,appInfoList);
        }
        adapter.notifyDataSetChanged();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
