package com.example.meetdoctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.event.CheckStateEvent;
import com.example.meetdoctor.ui.launcher.LauncherDelegate;
import com.example.meetdoctor.ui.launcher.ScrollLauncherTag;
import com.example.meetdoctor.core.storage.LattePreference;
import com.example.meetdoctor.ui.user.LoginDelegate;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.TimerHelper;
import com.google.gson.Gson;

public class WelcomeDelegate extends LatteDelegate {

    @SuppressWarnings("unused")
    private static final String TAG = "WelcomeActivity";
    private TimerHelper mTimer;

    @Override
    public Object setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimer = new TimerHelper() {
            @Override
            public void run() {
                // 如果是首次进入
                if (!LattePreference.getAppFlag(
                        ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
                    WelcomeDelegate.this.startWithPop(new LauncherDelegate());
                } else {
                    EventBusUtils.post(getProxyActivity(),
                            new EventMessage(EventCode.NOT_FIRST_LAUNCHER_APP));
                }
            }
        };
        if (LattePreference.getAppFlag(
                ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            mTimer.start(2000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.stop();
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        super.onReceiveEvent(event);
        switch (event.getCode()) {
            case EventCode.PERMISSION_SUCCESS:
                // 不用进行requestCode的检测直接开始计时
                // 延迟2s后执行，period的10000可忽略
                mTimer.start(2000);
                break;
            case EventCode.NOT_FIRST_LAUNCHER_APP:
                // 判断登录状态
                HttpUtils.checkLogin(response -> {
                    // 已经登录则发送进入问询页面请求
                    // 发送粘性事件，当打开HomeActivity时触发
                    HttpUtils.checkState((stateResponse) -> {
                        LatteLogger.d(stateResponse);
                        CheckStateEvent bean = new Gson()
                                .fromJson(stateResponse, CheckStateEvent.class);
                        EventBusUtils.postSticky(getProxyActivity(),
                                new EventMessage<>(EventCode.SUCCESS, bean));
                        startWithPop(new HomeDelegate());
                    });
                }, (code, msg) -> {
                    if (code == 406) {
                        startWithPop(new LoginDelegate());
                    }
                });
                break;
            default:
                break;
        }
    }
}