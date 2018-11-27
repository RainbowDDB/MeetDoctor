package com.example.meetdoctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.TimerHelper;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    private TimerHelper timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new TimerHelper() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                stop();
                Log.d("fuck", "fuck");
                finish();
            }
        };
        Log.d("fuck", "fuck");
        // 延迟3s后执行，period的10000可忽略
        timer.start(3000, 10000);
    }

    @Override
    public void initView() {
        TextView skip = findViewById(R.id.tv_skip);
        skip.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                timer.stop();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}
