package com.example.meetdoctor.core.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

/**
 * 基础UI布局
 * 解决各种UI问题
 * 生命周期：
 * -> onActivityCreated()
 * -> onCreate()
 * // -> onResume() 暂时未知，可能为activity的...
 * -> onLazyInitView()
 * -> onSupportVisible
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
