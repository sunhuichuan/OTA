package com.yao.ota.app.base.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.yao.dependence.ui.BaseActivity;
import com.yao.ota.app.DroidApplication;

/**
 * 自定义ViewGroup使用的BaseLayout
 * Created by huichuan on 2017/3/31.
 */
public abstract class BaseFrameLayout extends FrameLayout {

    protected Context appContext = DroidApplication.getApplication();
    /**
     * 强制用BaseActivity来构造自定义Layout
     */
    protected BaseActivity thisContext;


    public BaseFrameLayout(Context context) {
        super(context);
        initConstruct(context);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConstruct(context);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConstruct(context);
    }


    /**
     * 初始化构造配置
     * @param context
     */
    protected void initConstruct(Context context){
        thisContext = (BaseActivity) context;

    }


    protected abstract void initView(Context context);



}
