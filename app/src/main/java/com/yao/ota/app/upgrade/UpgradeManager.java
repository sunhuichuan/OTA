package com.yao.ota.app.upgrade;

import com.android.volley.Request;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.ota.app.base.network.request.HttpRequest;
import com.yao.ota.app.base.network.request.HttpStringRequest;
import com.yao.ota.app.feed.controller.FeedContainerController;
import com.yao.ota.app.feed.model.OtaAppInfo;
import com.yao.ota.app.feed.model.OtaInfo;
import com.yao.ota.app.feed.utils.AppFeedParseUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Class
 * Created by huichuan on 2017/4/21.
 */
public class UpgradeManager {
    private static final String TAG = "UpgradeManager";
    private static UpgradeManager ourInstance = new UpgradeManager();

    public static UpgradeManager getInstance() {
        return ourInstance;
    }

    private UpgradeManager() {
    }



    public void checkUpgrade(final HttpRequest.RequestCallback<OtaAppInfo> callback){

        String requestUrl = "http://ota.client.weibo.cn/android/packages/com.sina.app.weiboheadline?pkg_type=99";

        HttpStringRequest request = new HttpStringRequest(Request.Method.GET, requestUrl, new HttpRequest.RequestCallback<String>() {
            @Override
            public void onResponse(String response) {
//                LoggerUtil.i(TAG, "结果：" + response);
                Document parse = Jsoup.parse(response);
                List<OtaInfo> appList = AppFeedParseUtils.parseAppList(parse);
                LoggerUtil.e(TAG,"appName:"+appList);

                OtaAppInfo appInfo = null;
                if (appList!=null){
                    appInfo = (OtaAppInfo) appList.get(0);
                }
                callback.onResponse(appInfo);
            }

            @Override
            public void onErrorResponse(Exception error) {
                LoggerUtil.i(TAG, "错误：" + error);
                callback.onErrorResponse(error);
            }
        });

        request.addToRequestQueue();
    }


}
