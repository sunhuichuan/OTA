package com.yao.ota.app.base.controller;

import android.content.Context;
import android.view.View;

import com.yao.ota.app.DroidApplication;

/**
 * Adapter中使用的CardView的Controller
 * Created by huichuan on 2017/2/28.
 */
public abstract class BaseCardViewController<T> {

    protected Context appContext = DroidApplication.getApplication();
    //被管理控制的View
    protected View mControlledView;
    /**
     * View绑定的数据对象
     */
    private T objInfo;

    private boolean hasInitView = false;

    public BaseCardViewController(View viewGroup){
        mControlledView = viewGroup;
    }


    /**
     * 获取controller控制的ViewGroup
     * @return
     */
    protected View getControlledView(){
        return mControlledView;
    }

    /**
     * 获取controller对应的Obj对象
     * @return
     */
    protected T getObjInfo() {
        return objInfo;
    }

    /**
     * 外部调用的更新方法
     * @param objInfo
     */
    public final void update(T objInfo){
        this.objInfo = objInfo;
        if (!hasInitView){
            initLayoutView();
            hasInitView = true;
        }
        updateView();
    }

    /**
     * 初始化ViewLayout
     */
    protected abstract void initLayoutView();

    /**
     * 调用此方法前对象已经已经设置完毕
     */
    protected abstract void updateView();

    /**
     * 包装findViewById的方法
     * @param viewId
     * @return
     */
    protected View findViewById(int viewId){
        View view = mControlledView.findViewById(viewId);
        return view;
    }



}
