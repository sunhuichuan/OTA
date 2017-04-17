package com.yao.ota.app;

import android.app.Application;
import android.content.Context;
import com.yao.devsdk.SdkConfig;
import com.yao.devsdk.log.LoggerUtil;
import com.yao.devsdk.utils.ProcessUtils;
import com.yao.ota.R;
import com.yao.ota.app.constant.XConstants;
import com.yao.ota.app.tools.PrefsTools;

public class DroidApplication extends Application {
	private static final String TAG = "AppApplication";

	private static DroidApplication mApplication = null;

	public static DroidApplication getApplication() {
		return mApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		try {
			if (ProcessUtils.isMainProcess(mApplication)) {
                //主进程才执行app的init操作
				initConfig(this);
				initApp();
			}
		} catch (Exception e) {
			LoggerUtil.e(TAG, "初始化application init异常", e);
		}

	}



	/** 初始化配置信息 */
	private void initConfig(Context appContext) {
        //sdk的init要先于app本身的
		SdkConfig.initBaseConfig(appContext, XConstants.DEBUG, mApplication.getString(R.string.app_name));
		BaseConfig.initBaseConfig(appContext);
	}






	private void initApp() {
		//进程挂了重启，开关一定是关闭的
		PrefsTools.getInstance().is_forbid_video_ad.setVal(false).apply();


	}


}
