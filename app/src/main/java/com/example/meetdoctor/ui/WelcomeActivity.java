package com.example.meetdoctor.ui;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.event.CheckPermissionEvent;
import com.example.meetdoctor.model.event.CheckStateEvent;
import com.example.meetdoctor.ui.launcher.LauncherActivity;
import com.example.meetdoctor.ui.launcher.ScrollLauncherTag;
import com.example.meetdoctor.core.storage.LattePreference;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.TimerHelper;

public class WelcomeActivity extends BaseActivity {

    @SuppressWarnings("unused")
    private static final String TAG = "WelcomeActivity";
    private TimerHelper timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timer = new TimerHelper() {
            @Override
            public void run() {
                // 如果是首次进入
                if (!LattePreference.getAppFlag(
                        ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
                    startActivity(LauncherActivity.class);
                } else {
                    // 创建Looper对象当内部执行完成再执行后续操作，控制线程执行顺序
                    Looper.prepare();
                    // 判断登录状态
                    HttpUtils.checkLogin(response -> {
                        // 已经登录则发送进入问询页面请求
                        // 发送粘性事件，当打开HomeActivity时触发
                        HttpUtils.checkState(
                                (stateResponse) -> {
                                    LatteLogger.d(stateResponse);
                                    EventBusUtils.postSticky(
                                            new EventMessage<>(EventCode.SUCCESS, new CheckStateEvent(stateResponse)));
                                    startActivity(HomeActivity.class);
                                });
                    });
                    Looper.loop();
                }
                // 虽然TimerTask.cancel()提供了一个及时取消的接口
                // 但却没有一个自动机制保证失效的任务及时回收（可能需要用户手动处理）
                stop();
                // 最后finish是要保证timer能正常cancel，防止leaked
                finish();
            }
        };
        if (LattePreference.getAppFlag(
                ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            timer.start(1000, 10000);
        }
    }

    @Override
    public void initView() {
    }

    @Override
    public Object getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        super.onReceiveEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            if (event.getData() instanceof CheckPermissionEvent) {
                // 不用进行requestCode的检测直接开始计时
                // 延迟2s后执行，period的10000可忽略
                timer.start(2000, 10000);
            }
        }
    }
}
