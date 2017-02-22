package com.yao.ota.app.feed;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yao.devsdk.components.model.ActivityIntent;
import com.yao.devsdk.log.LogUtil;
import com.yao.devsdk.utils.ActivityUtil;
import com.yao.ota.R;
import com.yao.ota.app.activity.ToolsActivity;
import com.yao.ota.app.base.network.request.HttpRequest;
import com.yao.ota.app.base.network.request.HttpStringRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 专注于业务的Activity,其他控件放在base里面
 */
public class MainActivity extends MainBaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initViews();
    }

    void initViews() {
        String requestUrl = "http://ota.client.weibo.cn/android/packages/com.sina.app.weiboheadline";

        HttpStringRequest request = new HttpStringRequest(Request.Method.GET, requestUrl, new HttpRequest.RequestCallback<String>() {
            @Override
            public void onResponse(String response) {
                LogUtil.i(TAG, "结果：" + response);
                Document parse = Jsoup.parse(response);
                LogUtil.i(TAG, "document:" + parse);
            }

            @Override
            public void onErrorResponse(Exception error) {
                LogUtil.i(TAG, "错误：" + error);
            }
        });

        request.addToRequestQueue();


    }

    @Override
    protected void onClickMenuItem(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            //工具条目
            ActivityIntent intent = new ActivityIntent(thisContext, ToolsActivity.class);
            ActivityUtil.startActivityWithTranslationAnimation(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


    }
}
