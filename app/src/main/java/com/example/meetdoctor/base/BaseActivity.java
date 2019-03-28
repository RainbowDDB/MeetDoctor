package com.example.meetdoctor.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.utils.UIHelper;

/**
 * 基础UI布局
 * 解决各种UI问题
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

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(View view) {
        IBinder token = view.getWindowToken();
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
