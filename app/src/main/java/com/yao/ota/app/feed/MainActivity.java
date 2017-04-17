package com.yao.ota.app.feed;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.yao.devsdk.components.model.ActivityIntent;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.devsdk.utils.ActivityUtil;
import com.yao.ota.R;
import com.yao.ota.app.activity.ToolsActivity;
import com.yao.ota.app.base.network.request.HttpRequest;
import com.yao.ota.app.base.network.request.HttpStringRequest;
import com.yao.ota.app.feed.controller.FeedContainerController;
import com.yao.ota.app.feed.layout.FeedContainerLayout;
import com.yao.ota.app.feed.utils.AppFeedParseUtils;
import com.yao.ota.app.tools.layout.ToolsContainerLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 专注于业务的Activity,其他控件放在base里面
 */
public class MainActivity extends MainBaseActivity {

    private static final String TAG = "MainActivity";
    private ViewGroup contentRootView;
    private DrawerLayout drawer_layout;
    private TabLayout tl_tabs_layout;
    private FeedContainerController fcc_appFeedContainerController;
    private ToolsContainerLayout tcl_tools_layout_container;
    //Layout更新配置list
    private List<IMainFeedLayoutConfig> layoutConfigList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        contentRootView = (ViewGroup) findViewById(R.id.cl_main_layout_container);
        tl_tabs_layout = (TabLayout) findViewById(R.id.tl_tabs_layout);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tcl_tools_layout_container = (ToolsContainerLayout) findViewById(R.id.tcl_tools_layout_container);
        registerLayoutConfig(tcl_tools_layout_container);

        initViews();

    }

    //注册接受配置的layout
    private void registerLayoutConfig(IMainFeedLayoutConfig feedLayoutConfig){
        layoutConfigList.add(feedLayoutConfig);
    }

    private void unregisterLayoutConfig(IMainFeedLayoutConfig feedLayoutConfig){
        layoutConfigList.remove(feedLayoutConfig);
    }

    /**
     * 通知更新layoutConfig
     * @param updateConfig
     */
    private void notifyLayoutConfigList(IMainFeedLayoutConfig.UpdateConfig updateConfig){
        for (IMainFeedLayoutConfig config : layoutConfigList){
            config.update(updateConfig);
        }
    }



    void initViews() {
        String requestUrl = "http://ota.client.weibo.cn/android/packages/com.sina.app.weiboheadline";

        HttpStringRequest request = new HttpStringRequest(Request.Method.GET, requestUrl, new HttpRequest.RequestCallback<String>() {
            @Override
            public void onResponse(String response) {
//                LoggerUtil.i(TAG, "结果：" + response);
                Document parse = Jsoup.parse(response);
                LoggerUtil.i(TAG, "document:" + parse);
                List<String> appTypeNameList = AppFeedParseUtils.parseAppTypeNameList(parse);

                LoggerUtil.e(TAG,"appName:"+appTypeNameList);

                //填充ViewPager
                fcc_appFeedContainerController = new FeedContainerController(contentRootView,getSupportFragmentManager());
                fcc_appFeedContainerController.inflateData(appTypeNameList);
                registerLayoutConfig(fcc_appFeedContainerController);
            }

            @Override
            public void onErrorResponse(Exception error) {
                LoggerUtil.i(TAG, "错误：" + error);
            }
        });

        request.addToRequestQueue();


    }





    @Override
    protected void onClickMenuItem(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_app_list) {
            // show app list
            notifyLayoutConfigList(new IMainFeedLayoutConfig.UpdateConfig(IMainFeedLayoutConfig.PAGE_ID_MAIN_FEED_APP));
        } else if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            //工具条目
//            ActivityIntent intent = new ActivityIntent(thisContext, ToolsActivity.class);
//            ActivityUtil.startActivityWithDefaultAnimation(intent);
//            drawer_layout.closeDrawers();
            notifyLayoutConfigList(new IMainFeedLayoutConfig.UpdateConfig(IMainFeedLayoutConfig.PAGE_ID_TOOLS));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


    }
}
