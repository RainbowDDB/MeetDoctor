package com.example.meetdoctor.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.utils.UIHelper;

public class RetrieveSuccessDelegate extends LatteDelegate implements View.OnClickListener {

    private Button confirm;

    @Override
    public Object setLayout() {
        return R.layout.activity_retrieve_success;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        AppBarLayout appBarLayout = rootView.findViewById(R.id.app_bar);
        int statusBarHeight = UIHelper.getStatusBarHeight(getProxyActivity());
        appBarLayout.setPadding(0, statusBarHeight, 0, 0);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setToolbar(toolbar);

        confirm = rootView.findViewById(R.id.btn_confirm);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        confirm.setOnClickListener(this);
    }

    // 需复写BaseActivity中的finish方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                start(new LoginDelegate());
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                // finish掉 密保、修改密码的活动，直接回到登录页
                // 无需进行finish()，因为LoginDelegate的启动方式为singleTask TODO?
                start(new LoginDelegate(), SINGLETASK);
                break;
        }
    }
}
