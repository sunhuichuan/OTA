package com.yao.ota.app.feed.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yao.dependence.ui.BaseFragment;
import com.yao.ota.R;
import com.yao.ota.app.feed.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 */
public class FragmentOtaApp extends BaseFragment {

    @Bind(R.id.rv_recyclerView)
    RecyclerView recyclerView;
    List<String> datas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_app, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        initData();
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(datas);
        recyclerView.setAdapter(adapter);

        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v) {
                Snackbar.make(v, "Click Item "+v.getTag(), Snackbar.LENGTH_LONG).show();
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
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
