package com.example.meetdoctor.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.img.GlideCache;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class SettingsDelegate extends SettingsBaseDelegate implements View.OnClickListener {

    private TextView cacheMemory;

    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        setTitle("设置");

        RelativeLayout switchObject = rootView.findViewById(R.id.rl_switch_object);
        RelativeLayout security = rootView.findViewById(R.id.rl_account_security);
        RelativeLayout clear = rootView.findViewById(R.id.rl_clear_storage);
        RelativeLayout about = rootView.findViewById(R.id.rl_about_ai);
        cacheMemory = rootView.findViewById(R.id.tv_cache_memory);

        switchObject.setOnClickListener(this);
        security.setOnClickListener(this);
        clear.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        cacheMemory.setText(GlideCache.getInstance().getCacheSize(getProxyActivity()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_switch_object:
                start(new SwitchDelegate());
                break;
            case R.id.rl_account_security:
                break;
            case R.id.rl_clear_storage:
                GlideCache.getInstance().clearImageAllCache(getProxyActivity());
                cacheMemory.setText(GlideCache.getInstance().getCacheSize(getProxyActivity()));
                break;
            case R.id.rl_about_ai:
                break;
            default:
                break;
        }
    }
}
