package com.yao.ota.app.feed.mvp;

import com.android.volley.Request;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.ota.app.base.network.request.HttpRequest;
import com.yao.ota.app.base.network.request.HttpStringRequest;
import com.yao.ota.app.feed.model.LoadMoreInfo;
import com.yao.ota.app.feed.model.OtaInfo;
import com.yao.ota.app.feed.utils.AppFeedParseUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * AppList的Presenter
 * Created by huichuan on 2017/4/18.
 */
public class AppListPresenter implements AppListContract.Presenter {
    private static final String TAG = "AppListPresenter";

    private AppListContract.View view;
    private String appTypeName;
    private int loadPageNumber = 0;

    public AppListPresenter( AppListContract.View view){
        this.view = view;
    }

    @Override
    public void start() {

       appTypeName = view.getAppTypeName();

        requestDataFromServer();
    }


    @Override
    public void loadMore() {
        loadPageNumber++;
        requestDataFromServer();
    }


    /**
     * 从服务器请求数据
     */
    private void requestDataFromServer(){
        String requestUrl = "http://ota.client.weibo.cn/android/packages/com.sina.app.weiboheadline?page="+loadPageNumber+"&pkg_type="+appTypeName;

        HttpStringRequest request = new HttpStringRequest(Request.Method.GET, requestUrl, new HttpRequest.RequestCallback<String>() {
            @Override
            public void onResponse(String response) {
//                LoggerUtil.i(TAG, "结果：" + response);
                Document parse = Jsoup.parse(response);
                LoggerUtil.i(TAG, "document:" + parse);

                List<OtaInfo> appList = AppFeedParseUtils.parseAppList(parse);

                LoggerUtil.e(TAG,"appName:"+appList);
                if (loadPageNumber == 0){
                    //第一次加载
                    view.setAppInfoList(appList);
                }else{
                    //loadmore
                    view.addAppInfoListToAdapter(appList);
                }
            }

            @Override
            public void onErrorResponse(Exception error) {
                LoggerUtil.i(TAG, "错误：" + error);
            }
        });

        request.addToRequestQueue();
    }
}
