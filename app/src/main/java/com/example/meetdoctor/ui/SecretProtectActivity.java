package com.example.meetdoctor.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;

public class SecretProtectActivity extends BaseActivity implements View.OnClickListener {

    private Button nextStep;
    private EditText answer;

    @Override
    public void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        nextStep = findViewById(R.id.btn_next_step);
        answer = findViewById(R.id.edt_secret_answer);

        nextStep.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
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
