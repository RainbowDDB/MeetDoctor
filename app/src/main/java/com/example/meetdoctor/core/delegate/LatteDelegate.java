package com.example.meetdoctor.core.delegate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.meetdoctor.utils.UIHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 基础UI布局
 * 解决各种UI问题
 * 生命周期：
 * -> onActivityCreated()
 * -> onCreate()
 * -> onResume()
 * -> onSupportVisible
 * -> onLazyInitView()
 * -> onSupportInvisible()
 * -> onPause()
 */
public abstract class LatteDelegate extends PermissionCheckerDelegate {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity中仅有一个此fragment时（即仅有当前fragment在栈顶），则禁止滑动返回
        if (getPreFragment() == null) {
            setSwipeBackEnable(false);
        }
    }

    protected void setToolbar(Toolbar toolbar) {
        getProxyActivity().setSupportActionBar(toolbar);
        ActionBar actionBar = getProxyActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
