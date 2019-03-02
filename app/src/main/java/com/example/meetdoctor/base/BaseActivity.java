package com.example.meetdoctor.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.meetdoctor.utils.UIHelper;

/**
 * 基础UI布局
 */
public abstract class BaseActivity extends PermissionCheckerActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        UIHelper.setImmersiveStatusBar(getWindow());
        // 状态栏字体颜色
        UIHelper.setAndroidNativeLightStatusBar(this, true);
    }
}
