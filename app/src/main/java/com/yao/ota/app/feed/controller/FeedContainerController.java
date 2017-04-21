package com.yao.ota.app.feed.controller;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.yao.ota.R;
import com.yao.ota.app.feed.IMainFeedLayoutConfig;
import com.yao.ota.app.feed.fragment.FragmentOtaApp;

import java.util.List;


/**
 * Feed容器的controller
 * Created by huichuan on 2017/2/28.
 */
public class FeedContainerController implements IMainFeedLayoutConfig{


    private Activity thisContext;
    private FragmentManager mFragmentManager;
    private View rootView;
    //显示频道的TabLayout
    private TabLayout tl_tabs_layout;
    //显示app集合的ViewPager
    private ViewPager vp_viewpager;
    private AppPagerAdapter appPagerAdapter;

    public FeedContainerController(ViewGroup viewGroup,FragmentManager fm) {
        rootView = viewGroup;
        mFragmentManager = fm;
        thisContext = (Activity) viewGroup.getContext();
        tl_tabs_layout = (TabLayout) rootView.findViewById(R.id.tl_tabs_layout);
        vp_viewpager = (ViewPager) rootView.findViewById(R.id.vp_viewpager);
    }


    /**
     * 填充数据
     */
    public void inflateData(String packageName,List<String> nameList){
        if (appPagerAdapter == null){
            appPagerAdapter = new AppPagerAdapter(mFragmentManager);
        }
        appPagerAdapter.setPackageName(packageName);
        appPagerAdapter.setAppTypeNameList(nameList);
        vp_viewpager.setAdapter(appPagerAdapter);
        tl_tabs_layout.setupWithViewPager(vp_viewpager);
        vp_viewpager.post(new Runnable() {
            @Override
            public void run() {
                //默认选择第二个tab，第二个tab存放的是正式包
                vp_viewpager.setCurrentItem(1,false);
            }
        });

    }

    @Override
    public void update(UpdateConfig updateConfig) {
        String showingPageId = updateConfig.getShowingPageId();
        if (TextUtils.equals(showingPageId,IMainFeedLayoutConfig.PAGE_ID_MAIN_FEED_APP)){
            //app列表需要显示
            tl_tabs_layout.setVisibility(View.VISIBLE);
            vp_viewpager.setVisibility(View.VISIBLE);
        }else{
            //别人显示的时候，app列表隐藏
            tl_tabs_layout.setVisibility(View.GONE);
            vp_viewpager.setVisibility(View.GONE);
        }

    }


    public class AppPagerAdapter extends FragmentPagerAdapter{
        private String packageName;
        private List<String> appTypeNameList;

        public AppPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public void setAppTypeNameList(List<String> appTypeNameList) {
            this.appTypeNameList = appTypeNameList;
        }

        /**
         * 获取包名
         * @return
         */
        public String getPackageName() {
            return packageName;
        }

        @Override
        public int getCount() {
            return appTypeNameList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view,object);
//            return false;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FragmentOtaApp.newInstance(this,"99");
                default:
                    //其他的都是按position来命名type的
                    return FragmentOtaApp.newInstance(this,String.valueOf(position-1));
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            String typeName = appTypeNameList.get(position);
            return typeName;
        }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//
//            ListView listView = new ListView(thisContext);
//
//            List<String> dataList = new ArrayList<>();
//
//            for (int i=0;i<30;i++){
//                dataList.add("item---position:"+position+",i:"+i);
//            }
//
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(thisContext,android.R.layout.simple_list_item_1);
//            adapter.addAll(dataList);
//
//            listView.setAdapter(adapter);
//
//            return listView;
//        }
    }







}
