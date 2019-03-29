package com.example.meetdoctor.ui.ask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.ImageUtils;
import com.example.meetdoctor.utils.UIHelper;
import com.example.meetdoctor.widget.Header;

public class ResultActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Header header = findViewById(R.id.header);
        setSupportActionBar(header.getToolbar());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initView() {
        ImageView baymax = findViewById(R.id.img_baymax);
        ImageUtils.showImg(this, R.drawable.baymax, baymax,
                UIHelper.dip2px(this, 150), UIHelper.dip2px(this, 191));
    }

    @Override
    protected Object getLayout() {
        return R.layout.activity_result;
    }
}
