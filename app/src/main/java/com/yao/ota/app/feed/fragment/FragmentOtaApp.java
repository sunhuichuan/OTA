package com.yao.ota.app.feed.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yao.dependence.ui.BaseFragment;
import com.yao.dependence.widget.feedlist.FeedListBase;
import com.yao.dependence.widget.recycler.listener.OnRcvScrollListener;
import com.yao.devsdk.adapter.AceRecyclerAdapter;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.devsdk.utils.SdkUtil;
import com.yao.ota.R;
import com.yao.ota.app.feed.adapter.AppInfoListAdapter;
import com.yao.ota.app.feed.controller.FeedContainerController;
import com.yao.ota.app.feed.model.OtaAppInfo;
import com.yao.ota.app.feed.model.OtaInfo;
import com.yao.ota.app.feed.mvp.AppListContract;
import com.yao.ota.app.feed.mvp.AppListPresenter;
import com.yao.ota.app.feed.requestcallback.FeedRequestCallback;
import com.yao.ota.app.feed.widget.FeedListApp;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * OTA上App的列表
 */
public class FragmentOtaApp extends BaseFragment implements AppListContract.View {
    private static final String TAG = "FragmentOtaApp";

    public static final String ARGS_KEY_TYPE_NAME = "app_type_name";

    @Bind(R.id.frv_feedList)
    FeedListApp frv_feedList;
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

        recyclerView = frv_feedList.getListView();
        adapter=new AppInfoListAdapter();
        frv_feedList.setAdapter(adapter);
        registerForContextMenu(recyclerView);


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

            @Override
            public boolean onItemLongClick(View view, int position, OtaInfo item) {
                recyclerView.setTag(item);
                return super.onItemLongClick(view, position, item);
            }
        });

        recyclerView.addOnScrollListener(new OnRcvScrollListener(){
            @Override
            public void onBottom() {
                //滚动到了底部
            }
        });

        appListPresenter.start();

        frv_feedList.setCallbacks(new FeedRequestCallback(getAppTypeName(),appPagerAdapter.getPackageName()));
        frv_feedList.setLoadingListener(new FeedListBase.SimpleLoadingListener(frv_feedList));
        frv_feedList.setCanLoadMore(true);
        frv_feedList.setCanPullToLoad(false);
        frv_feedList.startLoadData();

        return view;
    }

    @Override
    public void setPresenter(AppListContract.Presenter presenter) {
    }

    private String getAppTypeName() {
        Bundle arguments = getArguments();
        if (arguments!=null){
            appTypeName = arguments.getString(ARGS_KEY_TYPE_NAME);
        }
        return appTypeName;
    }

    @Override
    public void showSnackToast(String toastText) {
        //显示toast
        Snackbar.make(frv_feedList, toastText, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final OtaAppInfo appInfo = (OtaAppInfo) v.getTag();
        MenuItem menuItem = menu.add(0, 1, Menu.NONE, "复制下载地址分享到qq");
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                copyOidToClipboard(appInfo);
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void copyOidToClipboard(OtaAppInfo appInfo){
        if (appInfo == null){
            return;
        }
        ClipboardManager cm = (ClipboardManager) appContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        String content = appInfo.getAppDownloadUrl()+"\n"+appInfo.getAppVersionName()+"\n"+appInfo.getAppDescription();
        cm.setPrimaryClip(ClipData.newPlainText(null, content));
        SdkUtil.showToast(appContext,"复制成功，快去转发吧");
        //打开qq
        try {
//            String url="mqqwpa://im/chat?chat_type=wpa&uin=123";
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

            startActivity(thisContext.getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq"));
        } catch (Exception e) {
            LoggerUtil.e(TAG,"打开qq异常",e);
        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
