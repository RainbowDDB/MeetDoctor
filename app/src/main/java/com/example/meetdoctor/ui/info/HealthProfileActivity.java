package com.example.meetdoctor.ui.info;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.UIHelper;

public class HealthProfileActivity extends BaseActivity {

    private ImageView editInfo;

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

        editInfo = findViewById(R.id.img_edit_info);
        editInfo.setOnClickListener(view -> {
            // 编辑个人信息
        });
    }

    @Override
    public Object getLayout() {
        return R.layout.activity_health_profile;
    }
}
