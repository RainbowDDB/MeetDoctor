package com.example.meetdoctor.ui;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.UIHelper;

public class RetrieveSuccessActivity extends BaseActivity implements View.OnClickListener {
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


        Button confirm = findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(this);
    }

    @Override
    public Object getLayout() {
        return R.layout.activity_retrieve_success;
    }

    // 需复写BaseActivity中的finish方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(LoginActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                // finish掉 密保、修改密码的活动，直接回到登录页
                // 无需进行finish()，因为LoginActivity的启动方式为singleTask
                startActivity(LoginActivity.class);
                break;
        }
    }
}
