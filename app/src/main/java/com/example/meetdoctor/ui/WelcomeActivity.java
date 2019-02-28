package com.example.meetdoctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.ui.launcher.LauncherActivity;
import com.example.meetdoctor.ui.launcher.ScrollLauncherTag;
import com.example.meetdoctor.core.storage.LattePreference;
import com.example.meetdoctor.utils.TimerHelper;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimerHelper timer = new TimerHelper() {
            @Override
            public void run() {
                // 如果是首次进入
                if (!LattePreference.getAppFlag(WelcomeActivity.this,
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
        // 延迟2s后执行，period的10000可忽略
        timer.start(2000, 10000);
    }

    @Override
    public void initView() {
    }

    @Override
    public Object getLayout() {
        return R.layout.activity_welcome;
    }
}
