package com.yao.ota.app.feed.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.yao.dependence.ui.BaseFragment;
import com.yao.devsdk.adapter.AceRecyclerAdapter;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.ota.R;
import com.yao.ota.app.base.network.request.HttpRequest;
import com.yao.ota.app.base.network.request.HttpStringRequest;
import com.yao.ota.app.feed.adapter.AppInfoListAdapter;
import com.yao.ota.app.feed.model.OtaAppInfo;
import com.yao.ota.app.feed.utils.AppFeedParseUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 */
public class FragmentOtaApp extends BaseFragment {
    private static final String TAG = "FragmentOtaApp";

    public static final String ARGS_KEY_TYPE_NAME = "app_type_name";

    @Bind(R.id.rv_recyclerView)
    RecyclerView recyclerView;
    List<String> datas;

    private String appTypeName;

    AppInfoListAdapter adapter;


    public static FragmentOtaApp newInstance(String typeName){
        FragmentOtaApp fragmentOtaApp = new FragmentOtaApp();
        Bundle argsBundle = new Bundle();
        argsBundle.putString(ARGS_KEY_TYPE_NAME,typeName);
        fragmentOtaApp.setArguments(argsBundle);
        return fragmentOtaApp;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_app, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        initData();
        adapter=new AppInfoListAdapter();
        recyclerView.setAdapter(adapter);

        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

        adapter.setOnItemClickListener(new AceRecyclerAdapter.OnItemClickListener<OtaAppInfo>() {
            @Override
            public void onItemClick(View view, int position, OtaAppInfo item) {
//                Snackbar.make(view, "Click Item "+view.getTag(), Snackbar.LENGTH_LONG).show();
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getAppDownloadUrl()));
                thisContext.startActivity(it);
            }
        });


//        TextView tv_text = new TextView(thisContext);
//        tv_text.setText("aldjfaljdflasdjfkajdflasjdflasjdflasjdlfjasdlfalsdfkjalsdkfj");
//
//        view = tv_text;

        return view;
    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpacesItemDecoration(int space) {
            this.space=space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }
    public void initData(){
        datas = new ArrayList<String>();
        for(int i =0;i<17;i++){
            datas.add("item "+i);
        }

        Bundle arguments = getArguments();
        if (arguments!=null){
            appTypeName = arguments.getString(ARGS_KEY_TYPE_NAME);
        }

        String requestUrl = "http://ota.client.weibo.cn/android/packages/com.sina.app.weiboheadline?pkg_type="+appTypeName;

        HttpStringRequest request = new HttpStringRequest(Request.Method.GET, requestUrl, new HttpRequest.RequestCallback<String>() {
            @Override
            public void onResponse(String response) {
//                LoggerUtil.i(TAG, "结果：" + response);
                Document parse = Jsoup.parse(response);
                LoggerUtil.i(TAG, "document:" + parse);
                List<OtaAppInfo> appList = AppFeedParseUtils.parseAppList(parse);

                LoggerUtil.e(TAG,"appName:"+appList);

                adapter.setList(appList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(Exception error) {
                LoggerUtil.i(TAG, "错误：" + error);
            }
        });

        request.addToRequestQueue();




    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
