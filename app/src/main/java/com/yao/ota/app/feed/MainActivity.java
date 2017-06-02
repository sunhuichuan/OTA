package com.yao.ota.app.feed;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.yao.dependence.utils.DialogUtils;
import com.yao.dependence.widget.feedlist.LoadingInterface;
import com.yao.devsdk.SdkConfig;
import com.yao.devsdk.adapter.AceAdapter;
import com.yao.devsdk.imageloader.ImageLoaderManager;
import com.yao.devsdk.imageloader.ImageOption;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.devsdk.utils.DisplayUtil;
import com.yao.devsdk.utils.SdkUtil;
import com.yao.ota.R;
import com.yao.ota.app.base.network.request.HttpRequest;
import com.yao.ota.app.base.network.request.HttpStringRequest;
import com.yao.ota.app.feed.controller.FeedContainerController;
import com.yao.ota.app.feed.model.OtaAppCategory;
import com.yao.ota.app.feed.model.OtaAppInfo;
import com.yao.ota.app.feed.utils.AppFeedParseUtils;
import com.yao.ota.app.tools.layout.ToolsContainerLayout;
import com.yao.ota.app.upgrade.UpgradeManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
    private List<OtaAppCategory> appCategoryList;

    private static final String DEFAULT_APP_NAME = "微博头条";
    private static final String DEFAULT_PACKAGE_NAME = "com.sina.app.weiboheadline";
    private String showingAppName = DEFAULT_APP_NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        contentRootView = (ViewGroup) findViewById(R.id.cl_main_layout_container);
        tl_tabs_layout = (TabLayout) findViewById(R.id.tl_tabs_layout);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tcl_tools_layout_container = (ToolsContainerLayout) findViewById(R.id.tcl_tools_layout_container);
        registerLayoutConfig(tcl_tools_layout_container);

        //TODO 在这里初始化Controller有什么问题吗？
        initController();
        initViews(DEFAULT_PACKAGE_NAME);

        initAppList();

        checkUpdate();

    }

    void initController(){
        fcc_appFeedContainerController = new FeedContainerController(contentRootView,getSupportFragmentManager());
        fcc_appFeedContainerController.setReloadDataListener(new LoadingInterface.LoadingListener() {
            @Override
            public void onErrorViewClicked() {
                initViews(DEFAULT_PACKAGE_NAME);
            }

            @Override
            public void onNoDataViewClicked() {
                initViews(DEFAULT_PACKAGE_NAME);
            }

            @Override
            public void onNoNetViewClicked() {
                initViews(DEFAULT_PACKAGE_NAME);
            }
        });

        registerLayoutConfig(fcc_appFeedContainerController);
    }




    /**
     * 检查版本更新
     */
    private void checkUpdate() {
        UpgradeManager.getInstance().checkUpgrade(new HttpRequest.RequestCallback<OtaAppInfo>() {
            @Override
            public void onResponse(final OtaAppInfo appInfo) {
                try {
                    String appVersionName = appInfo.getAppVersionName();
                    int splitIndex = appVersionName.lastIndexOf("_");
                    String versionCode = appVersionName.substring(splitIndex+1);
                    if (Integer.parseInt(versionCode) > SdkConfig.versionCode){
                        //有新版本
                        DialogUtils.showDialog(thisContext, "提示", "OTA有新版本，赶快更新", "立即升级", "下次再说", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(appInfo.getAppDownloadUrl()));
                                thisContext.startActivity(it);
                            }
                        }, null);
                    }
                } catch (Exception e) {
                    LoggerUtil.e(TAG,"检查新版本异常",e);
                }
            }

            @Override
            public void onErrorResponse(Exception error) {
                LoggerUtil.e(TAG,"检查新版本异常",error);
            }
        });
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



    private FeedContainerController getFeedContainerController(){
        if (fcc_appFeedContainerController == null){
            fcc_appFeedContainerController = new FeedContainerController(contentRootView,getSupportFragmentManager());
        }
        return fcc_appFeedContainerController;
    }

    void initViews(final String packageName) {
        fcc_appFeedContainerController.onRequestData();

        String requestUrl = "http://ota.client.weibo.cn/android/packages/"+packageName;

        HttpStringRequest request = new HttpStringRequest(Request.Method.GET, requestUrl, new HttpRequest.RequestCallback<String>() {
            @Override
            public void onResponse(String response) {
//                LoggerUtil.i(TAG, "结果：" + response);
                Document parse = Jsoup.parse(response);
                LoggerUtil.i(TAG, "document:" + parse);
                List<String> appTypeNameList = AppFeedParseUtils.parseAppTypeNameList(parse);

                LoggerUtil.e(TAG,"appName:"+appTypeNameList);

                //填充ViewPager
                getFeedContainerController().onRequestDataSuccess(packageName,appTypeNameList);
                registerLayoutConfig(fcc_appFeedContainerController);
            }

            @Override
            public void onErrorResponse(Exception error) {
                LoggerUtil.i(TAG, "错误：" + error);
                getFeedContainerController().onRequestDataError();
            }
        });

        request.addToRequestQueue();


    }


    /**
     * 获取app列表
     */
    private void initAppList() {
        String requestUrl = "http://ota.client.weibo.cn/android/apps";

        HttpStringRequest request = new HttpStringRequest(Request.Method.GET, requestUrl, new HttpRequest.RequestCallback<String>() {
            @Override
            public void onResponse(String response) {
//                LoggerUtil.i(TAG, "结果：" + response);
                Document parse = Jsoup.parse(response);
                LoggerUtil.i(TAG, "documentCategory:" + parse);
                appCategoryList = AppFeedParseUtils.parseAppCategoryList(parse);

                LoggerUtil.e(TAG,"appCategoryList:"+appCategoryList);

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



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.getItem(0);
        item.setTitle("点击选择App:"+showingAppName);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_select_app_category) {
            LoggerUtil.i(TAG,"点击选择appItem");
            showSelectAppDialog();
            return true;
        }else if (id == R.id.action_settings) {
            //测试跳转到qq主页面
//            startActivity(getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq"));

//            String url="mqqwpa://im/chat?chat_type=wpa&uin=496398177";
//            String url="mqqwpa://";
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 展示选择app的对话框
     */
    private void showSelectAppDialog(){
        if (appCategoryList == null){
            SdkUtil.showToast(appContext,"请求出错，请重试");
            return;
        }
        AceAdapter<OtaAppCategory> aceAdapter = new AceAdapter<OtaAppCategory>() {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                OtaAppCategory otaAppCategory = getItem(position);
                int padding = DisplayUtil.dip2px(appContext, 5);
                LinearLayout linearLayout = new LinearLayout(thisContext);
                linearLayout.setPadding(padding,padding,padding,padding);
                linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                ImageView iv_appLogo = new ImageView(thisContext);
                iv_appLogo.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageLoaderManager.getInstance().displayImage(otaAppCategory.getLogoUrl(),iv_appLogo, ImageOption.getSmallOptions());
                int width = DisplayUtil.dip2px(appContext, 40);
                LinearLayout.LayoutParams iv_appLogoParams = new LinearLayout.LayoutParams(width,width);
                linearLayout.addView(iv_appLogo,iv_appLogoParams);

                TextView tv_appName = new TextView(thisContext);
                String appName = otaAppCategory.getAppName();
                if (appName.length()>7){
                    appName = appName.substring(0,8)+"……";
                }
                tv_appName.setText(appName);
                linearLayout.addView(tv_appName);

                TextView tv_appPackageName = new TextView(thisContext);
                tv_appPackageName.setText(otaAppCategory.getPackageName());
                tv_appPackageName.setMaxLines(1);
                tv_appPackageName.setEllipsize(TextUtils.TruncateAt.END);
                linearLayout.addView(tv_appPackageName);

                return linearLayout;
            }
        };
        aceAdapter.setList(appCategoryList);

        AlertDialog.Builder builder = new AlertDialog.Builder(thisContext);
        builder.setAdapter(aceAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OtaAppCategory otaAppCategory = appCategoryList.get(which);
                showingAppName = otaAppCategory.getAppName();
                initViews(otaAppCategory.getPackageName());
                dialog.cancel();

            }
        });

        builder.show();

    }



}
