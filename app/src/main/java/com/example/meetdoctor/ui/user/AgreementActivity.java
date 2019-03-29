package com.example.meetdoctor.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.UIHelper;
import com.example.meetdoctor.widget.Header;

public class AgreementActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Header header = findViewById(R.id.header);
        header.setTitle("遇医协议");
        setSupportActionBar(header.getToolbar());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void initView() {
    }

    @Override
    public Object getLayout() {
        return R.layout.activity_agreement;
    }
}
