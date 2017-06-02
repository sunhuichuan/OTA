package com.yao.ota.app.feed.mvp;

import com.yao.devsdk.mvp.BasePresenter;
import com.yao.devsdk.mvp.BaseView;
import com.yao.ota.app.feed.model.OtaInfo;

import java.util.List;

public class AppListContract {

    public interface View extends BaseView<Presenter> {
        //显示toast
        void showSnackToast(String toastText);

    }

    public interface Presenter extends BasePresenter<OtaInfo> {
        void loadMore();
    }

}
