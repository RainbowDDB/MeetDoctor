package com.example.meetdoctor.base;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

/**
 * 全局Application
 * 作用：初始化以及销毁一些东西
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 友盟统计注册
        UMConfigure.init(this, "5bf14e5ff1f5569ca50006aa", "YuYi"
                , UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);
    }
}
