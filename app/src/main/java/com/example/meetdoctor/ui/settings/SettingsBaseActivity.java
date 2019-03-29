package com.example.meetdoctor.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.UIHelper;
import com.example.meetdoctor.widget.Header;

/**
 * 黑色UI主题Settings页面
 * 包括:SwitchActivity, EditActivity, SettingsActivity
 */
public abstract class SettingsBaseActivity extends BaseActivity {

    private Header header;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏颜色为黑
        UIHelper.setStatusBarColor(getWindow(), getResources().getColor(R.color.textBlack));
        // 状态栏字体颜色为白
        UIHelper.setAndroidNativeLightStatusBar(this, false);

        header = findViewById(R.id.header);
        header.setTitleColor(getResources().getColor(R.color.pureWhite));
        header.setBackgroundColor(getResources().getColor(R.color.textBlack));

        setSupportActionBar(header.getToolbar());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setTitle(String title) {
        header.setTitle(title);
    }
}
