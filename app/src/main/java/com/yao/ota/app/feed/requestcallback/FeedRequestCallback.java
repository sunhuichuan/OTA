package com.yao.ota.app.feed.requestcallback;

import android.support.design.widget.Snackbar;

import com.android.volley.Request;
import com.yao.dependence.widget.feedlist.FeedListBase;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.ota.app.base.network.request.HttpRequest;
import com.yao.ota.app.base.network.request.HttpStringRequest;
import com.yao.ota.app.feed.model.OtaInfo;
import com.yao.ota.app.feed.utils.AppFeedParseUtils;
import com.yao.ota.app.feed.widget.FeedListApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.List;

/**
 * FeedList用于请求使用的callback
 * Created by huichuan on 2017/6/2.
 */
public class FeedRequestCallback implements FeedListBase.FeedListCallbacks {
    private static final String TAG = "FeedRequestCallback";

    private String appTypeName;
    private String appPackageName;
    private FeedListApp feedListApp;
    private int loadPageNumber;

    public FeedRequestCallback(String appTypeName, String appPackageName){
        this.appTypeName = appTypeName;
        this.appPackageName = appPackageName;
    }

    public void setFeedRecyclerView(FeedListApp feedListApp){
        this.feedListApp = feedListApp;
    }


    @Override
    public HashMap<String, String> getStartLoadParam() {
        HashMap<String,String> params = new HashMap<>();
        params.put("page","0");
        return params;
    }

    @Override
    public HashMap<String, String> getPullToLoadParam() {
        HashMap<String,String> params = new HashMap<>();
        params.put("page","0");
        return params;
    }

    @Override
    public HashMap<String, String> getLoadMoreParam() {
        HashMap<String,String> params = new HashMap<>();
        params.put("page",""+loadPageNumber);
        return params;
    }

    @Override
    public HashMap<String, String> getPullToReLoadParam() {
        HashMap<String,String> params = new HashMap<>();
        params.put("page","0");
        return params;
    }

    @Override
    public void startRequestFeed(final FeedListBase.RequestType requestType, HashMap<String, String> params) {

        String requestUrl = "http://ota.client.weibo.cn/android/packages/"+appPackageName+"?page="+loadPageNumber+"&pkg_type="+appTypeName;

        HttpStringRequest request = new HttpStringRequest(Request.Method.GET, requestUrl, new HttpRequest.RequestCallback<String>() {
            @Override
            public void onResponse(String response) {
//                LoggerUtil.i(TAG, "结果：" + response);
                Document parse = Jsoup.parse(response);
                LoggerUtil.i(TAG, "document:" + parse);

                List<OtaInfo> appList = null;
                try {
                    appList = AppFeedParseUtils.parseAppList(parse);
                } catch (Exception e) {
                    LoggerUtil.e(TAG,"异常",e);
                }
                LoggerUtil.e(TAG,"appName:"+appList);

                if (appList!=null){
                    if (requestType == FeedListBase.RequestType.TYPE_LOAD_MORE){
                        //加载更多
                        //计数器加一
                        loadPageNumber++;
                    }
                    feedListApp.onLoadDataOK(requestType,appList);
                }else{

                    //显示toast
                    Snackbar.make(feedListApp, "该类型没有更多App", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onErrorResponse(Exception error) {
                LoggerUtil.i(TAG, "错误：" + error);
                feedListApp.onLoadDataError(requestType,error);
            }
        });

        request.addToRequestQueue();
    }

    @Override
    public void abortRequestFeed() {

    }
}
