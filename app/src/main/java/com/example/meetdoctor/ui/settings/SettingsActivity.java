package com.example.meetdoctor.ui.settings;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.img.GlideCache;

public class SettingsActivity extends SettingsBaseActivity implements View.OnClickListener {

    private TextView cacheMemory;

    @Override
    protected void initView() {
        RelativeLayout switchObject = findViewById(R.id.rl_switch_object);
        RelativeLayout security = findViewById(R.id.rl_account_security);
        RelativeLayout clear = findViewById(R.id.rl_clear_storage);
        RelativeLayout about = findViewById(R.id.rl_about_ai);
        cacheMemory = findViewById(R.id.tv_cache_memory);
        cacheMemory.setText(GlideCache.getInstance().getCacheSize(this));

        switchObject.setOnClickListener(this);
        security.setOnClickListener(this);
        clear.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    @Override
    protected Object getLayout() {
        return R.layout.activity_settings;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_switch_object:
                startActivity(SwitchActivity.class);
                break;
            case R.id.rl_account_security:
                break;
            case R.id.rl_clear_storage:
                GlideCache.getInstance().clearImageAllCache(this);
                cacheMemory.setText(GlideCache.getInstance().getCacheSize(this));
                break;
            case R.id.rl_about_ai:
                break;
            default:
                break;
        }
    }
}
