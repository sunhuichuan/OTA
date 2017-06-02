package com.yao.ota.app.feed.mvp;

/**
 * AppList的Presenter
 * Created by huichuan on 2017/4/18.
 */
public class AppListPresenter implements AppListContract.Presenter {
    private static final String TAG = "AppListPresenter";

    private AppListContract.View view;

    public AppListPresenter( AppListContract.View view){
        this.view = view;
    }

    @Override
    public void start() {

    }


    @Override
    public void loadMore() {
    }


}
