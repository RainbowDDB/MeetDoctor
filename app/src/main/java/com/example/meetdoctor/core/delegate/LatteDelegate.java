package com.example.meetdoctor.core.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.meetdoctor.utils.UIHelper;

/**
 * 基础UI布局
 * 解决各种UI问题
 */
public abstract class LatteDelegate extends PermissionCheckerDelegate {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        UIHelper.setImmersiveStatusBar(getProxyActivity().getWindow());
        // 状态栏字体颜色
        UIHelper.setAndroidNativeLightStatusBar(getProxyActivity(), true);
    }
}
