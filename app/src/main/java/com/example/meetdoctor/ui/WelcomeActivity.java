package com.example.meetdoctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.event.CheckStateEvent;
import com.example.meetdoctor.ui.launcher.LauncherActivity;
import com.example.meetdoctor.ui.launcher.ScrollLauncherTag;
import com.example.meetdoctor.core.storage.LattePreference;
import com.example.meetdoctor.ui.user.LoginActivity;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.TimerHelper;

public class WelcomeActivity extends BaseActivity {

    @SuppressWarnings("unused")
    private static final String TAG = "WelcomeActivity";
    private TimerHelper mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTimer = new TimerHelper() {
            @Override
            public void run() {
                // 如果是首次进入
                if (!LattePreference.getAppFlag(
                        ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
                    startActivity(LauncherActivity.class);
                    finish();
                } else {
                    EventBusUtils.post(new EventMessage(EventCode.NOT_FIRST_LAUNCHER_APP));
                }
            }
        };
        if (LattePreference.getAppFlag(
                ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            mTimer.start(2000);
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
    protected void onDestroy() {
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
                        EventBusUtils.postSticky(
                                new EventMessage<>(EventCode.SUCCESS, new CheckStateEvent(stateResponse)));
                        startNewActivity(HomeActivity.class);
                    });
                }, (code, msg) -> {
                    if (code == 406) {
                        startNewActivity(LoginActivity.class);
                    }
                });
                break;
            default:
                break;
        }
    }
}