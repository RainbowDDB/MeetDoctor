package com.example.meetdoctor.ui.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.UIHelper;
import com.example.meetdoctor.widget.Header;

public class SecretProtectActivity extends BaseActivity implements View.OnClickListener {

    private Button nextStep;
    private EditText answer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIHelper.setStatusBarColor(getWindow(), Color.WHITE);

        Header header = findViewById(R.id.header);
        header.setTitle("密保问题");
        setSupportActionBar(header.getToolbar());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void initView() {
        ScrollView scrollView = findViewById(R.id.scroll_view);
        UIHelper.setScrollViewHeight(getWindow(), scrollView);

        nextStep = findViewById(R.id.btn_next_step);
        answer = findViewById(R.id.edt_secret_answer);

        nextStep.setOnClickListener(this);
    }

    @Override
    public Object getLayout() {
        return R.layout.activity_secret_protect;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next_step:
                startActivity(RetrievePasswordActivity.class);
                break;
        }
    }
}
