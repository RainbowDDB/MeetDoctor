package com.example.meetdoctor.core.app;

import android.app.Application;
import android.content.Context;

import com.example.meetdoctor.core.log.LatteLogger;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;

/**
 * 全局Application
 * 作用：初始化以及销毁一些东西
 * Created By Rainbow on 2019/4/30.
 */
public class App extends Application {

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

        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏  BUBBLE 显示
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true) // BuildConfig.DEBUG
                // 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                .handleException(e -> {
                    // 建议在该回调处上传至我们的Crash监测服务器
                    // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                    // Bugtags.sendException(e);
                    LatteLogger.e("Fragmentation ERROR", e.toString());
                })
                .install();

        // 友盟统计注册
        UMConfigure.init(this, "5bf14e5ff1f5569ca50006aa", "YuYi"
                , UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);
        // Logger初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
        LatteLogger.setLevel(LatteLogger.DEBUG);
    }
}
