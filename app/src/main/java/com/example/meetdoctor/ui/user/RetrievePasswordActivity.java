package com.example.meetdoctor.ui.user;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.UIHelper;

public class RetrievePasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText account, password, confirmPassword;
    private Button confirm;

    @Override
    public void initView() {
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setPadding(0, UIHelper.getStatusBarHeight(this), 0, 0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ScrollView scrollView = findViewById(R.id.scroll_view);
        UIHelper.setScrollViewHeight(getWindow(), scrollView);

        account = findViewById(R.id.edt_account);
        password = findViewById(R.id.edt_password);
        confirmPassword = findViewById(R.id.edt_confirm_password);
        confirm = findViewById(R.id.btn_confirm);

        confirm.setOnClickListener(this);
    }

    @Override
    public Object getLayout() {
        return R.layout.acticity_retrieve_password;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                startActivity(RetrieveSuccessActivity.class);
                break;
        }
    }
}
