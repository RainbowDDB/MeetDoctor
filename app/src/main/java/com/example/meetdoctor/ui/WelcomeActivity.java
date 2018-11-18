package com.example.meetdoctor.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv:
                startActivity(LoginActivity.class);
                break;
        }
    }
}
