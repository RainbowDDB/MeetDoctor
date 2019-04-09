package com.example.meetdoctor.ui.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.utils.UIHelper;

public class HealthProfileDelegate extends LatteDelegate {

    private ImageView editInfo;

    @Override
    public Object setLayout() {
        return R.layout.delegate_health_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        AppBarLayout appBarLayout = rootView.findViewById(R.id.app_bar);
        appBarLayout.setPadding(0, UIHelper.getStatusBarHeight(getProxyActivity()), 0, 0);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setToolbar(toolbar);

        editInfo = rootView.findViewById(R.id.img_edit_info);
        editInfo.setOnClickListener(view -> {
            // 编辑个人信息
        });
    }
}
