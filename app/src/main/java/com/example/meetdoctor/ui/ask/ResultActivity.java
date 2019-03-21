package com.example.meetdoctor.ui.ask;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.ImageUtils;
import com.example.meetdoctor.utils.UIHelper;

public class ResultActivity extends BaseActivity {
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

        ImageView baymax = findViewById(R.id.img_baymax);
        ImageUtils.showImg(this, R.drawable.baymax, baymax,
                UIHelper.dip2px(this, 150), UIHelper.dip2px(this, 191));
    }

    @Override
    protected Object getLayout() {
        return R.layout.activity_result;
    }
}
