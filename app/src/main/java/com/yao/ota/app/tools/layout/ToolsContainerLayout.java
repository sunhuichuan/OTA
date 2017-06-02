package com.yao.ota.app.tools.layout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.yao.ota.R;
import com.yao.ota.app.BaseConfig;
import com.yao.ota.app.base.layout.BaseFrameLayout;
import com.yao.ota.app.feed.IMainFeedLayoutConfig;
import com.yao.ota.app.tools.PrefsTools;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 自定义的ToolsLayout
 * Created by huichuan on 2017/3/31.
 */
public class ToolsContainerLayout extends BaseFrameLayout implements IMainFeedLayoutConfig, CompoundButton.OnCheckedChangeListener {



    @Bind(R.id.tv_app_info)
    public TextView tv_app_info;

    @Bind(R.id.sb_forbid_video_ad)
    public SwitchButton sb_forbid_video_ad;

    @Bind(R.id.sb_debug_enter_source_page)
    public SwitchButton sb_debug_enter_source_page;

    private PrefsTools prefsTools = PrefsTools.getInstance();


    public ToolsContainerLayout(Context context) {
        super(context);
    }

    public ToolsContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolsContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void initConstruct(Context context) {
        super.initConstruct(context);
        View.inflate(context,R.layout.activity_tools,this);
        ButterKnife.bind(this);

        sb_forbid_video_ad.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initView(Context context) {

    }


    @Override
    public void update(UpdateConfig updateConfig) {
        String showingPageId = updateConfig.getShowingPageId();
        if (TextUtils.equals(showingPageId,IMainFeedLayoutConfig.PAGE_ID_TOOLS)){
            //tools页面需要显示
            updateView();
            setVisibility(View.VISIBLE);
        }else{
            setVisibility(View.GONE);
        }
    }



    private void updateView(){
        if (prefsTools.is_forbid_video_ad.getVal()){
            sb_forbid_video_ad.setChecked(true);
        }

        tv_app_info.setText("本机工具信息\n版本号："+ BaseConfig.versionName);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == sb_forbid_video_ad){
            //点击了屏蔽视频广告开关
            Boolean isForbid = prefsTools.is_forbid_video_ad.getVal();
            prefsTools.is_forbid_video_ad.setVal(!isForbid).apply();
            if (prefsTools.is_forbid_video_ad.getVal()){
                //屏蔽广告

            }else{
                //不屏蔽广告
            }

        }else if (buttonView == sb_debug_enter_source_page){

        }
    }
}
