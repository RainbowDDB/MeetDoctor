package com.example.meetdoctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.event.CheckPermissionEvent;
import com.example.meetdoctor.ui.launcher.LauncherActivity;
import com.example.meetdoctor.ui.launcher.ScrollLauncherTag;
import com.example.meetdoctor.core.storage.LattePreference;
import com.example.meetdoctor.utils.TimerHelper;

public class WelcomeActivity extends BaseActivity {

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
                    startActivity(new Intent(WelcomeActivity.this, LauncherActivity.class));
                } else {
                    // 判断登录状态
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                stop();
                finish();
            }
        };
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
