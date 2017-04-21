package com.yao.ota.app.feed.mvp;

import com.yao.devsdk.mvp.BasePresenter;
import com.yao.devsdk.mvp.BaseView;
import com.yao.ota.app.feed.model.OtaInfo;

import java.util.List;

public class AppListContract {

    public interface View extends BaseView<Presenter> {
        //app的包名
        String getAppPackageName();
        //app列表的类型名字
        String getAppTypeName();
        //显示toast
        void showSnackToast(String toastText);

        /**
         * 设置appInfo集合
         * @param appInfoList
         */
        void setAppInfoList(List<OtaInfo> appInfoList);
        void addAppInfoListToAdapter(List<OtaInfo> appInfoList);
    }

    public interface Presenter extends BasePresenter<OtaInfo> {
        void loadMore();
    }

}
