package com.example.meetdoctor.ui.info;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.UIHelper;

public class CollectionActivity extends BaseActivity {
    @Override
    protected void initView() {
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
    protected Object getLayout() {
        return R.layout.activity_collection;
    }
}