package com.yao.ota.app.activity;

import android.content.Intent;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;
import com.yao.dependence.ui.BaseButterActivity;
import com.yao.ota.R;
import com.yao.ota.app.tools.PrefsTools;

import butterknife.Bind;

/**
 * 工具页面信息
 */
public class ToolsActivity extends BaseButterActivity implements CompoundButton.OnCheckedChangeListener {


    @Bind(R.id.sb_forbid_video_ad)
    public SwitchButton sb_forbid_video_ad;

    @Bind(R.id.sb_debug_enter_source_page)
    public SwitchButton sb_debug_enter_source_page;

    private PrefsTools prefsTools = PrefsTools.getInstance();

    @Override
    protected int contentViewLayoutId() {
        return R.layout.activity_tools;
    }

    @Override
    protected void initViews() {
        if (prefsTools.is_forbid_video_ad.getVal()){
            sb_forbid_video_ad.setChecked(true);
        }
        sb_forbid_video_ad.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton == sb_forbid_video_ad){
            //点击了屏蔽视频广告开关
            Boolean isForbid = prefsTools.is_forbid_video_ad.getVal();
            prefsTools.is_forbid_video_ad.setVal(!isForbid).apply();
            if (prefsTools.is_forbid_video_ad.getVal()){
                //屏蔽广告

            }else{
                //不屏蔽广告
            }

        }else if (compoundButton == sb_debug_enter_source_page){

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //activityResult
    }
}
