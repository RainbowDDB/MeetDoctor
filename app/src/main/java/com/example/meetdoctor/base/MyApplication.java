package com.example.meetdoctor.base;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;

/**
 * 全局Application
 * 作用：初始化以及销毁一些东西
 */
public class MyApplication extends Application {

    // 此处有坑，慎用！
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        //获取Context
        context = getApplicationContext();
        // 友盟统计注册
        UMConfigure.init(this, "5bf14e5ff1f5569ca50006aa", "YuYi"
                , UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);
        // Logger初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
