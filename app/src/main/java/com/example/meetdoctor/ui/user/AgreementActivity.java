package com.example.meetdoctor.ui.user;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.UIHelper;

public class AgreementActivity extends BaseActivity {
    @Override
    public void initView() {
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setPadding(0, UIHelper.getStatusBarHeight(this), 0, 0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public Object getLayout() {
        return R.layout.activity_agreement;
    }
}
