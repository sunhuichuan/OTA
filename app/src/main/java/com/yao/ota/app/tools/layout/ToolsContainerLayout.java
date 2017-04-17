package com.yao.ota.app.tools.layout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.yao.ota.app.base.layout.BaseFrameLayout;
import com.yao.ota.app.feed.IMainFeedLayoutConfig;

/**
 * 自定义的ToolsLayout
 * Created by huichuan on 2017/3/31.
 */
public class ToolsContainerLayout extends BaseFrameLayout implements IMainFeedLayoutConfig{
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
    protected void initView(Context context) {

    }


    @Override
    public void update(UpdateConfig updateConfig) {
        String showingPageId = updateConfig.getShowingPageId();
        if (TextUtils.equals(showingPageId,IMainFeedLayoutConfig.PAGE_ID_TOOLS)){
            //tools页面需要显示
            setVisibility(View.VISIBLE);
        }else{
            setVisibility(View.GONE);
        }
    }
}
