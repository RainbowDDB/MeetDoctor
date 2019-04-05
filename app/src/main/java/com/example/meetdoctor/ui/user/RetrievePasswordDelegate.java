package com.example.meetdoctor.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.utils.UIHelper;

public class RetrievePasswordDelegate extends LatteDelegate implements View.OnClickListener {

    private EditText account, password, confirmPassword;
    private Button confirm;


    @Override
    public Object setLayout() {
        return R.layout.acticity_retrieve_password;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        AppBarLayout appBarLayout = rootView.findViewById(R.id.app_bar);
        appBarLayout.setPadding(0, UIHelper.getStatusBarHeight(getProxyActivity()), 0, 0);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setToolbar(toolbar);

        ScrollView scrollView = rootView.findViewById(R.id.scroll_view);
        UIHelper.setScrollViewHeight(getProxyActivity().getWindow(), scrollView);

        account = rootView.findViewById(R.id.edt_account);
        password = rootView.findViewById(R.id.edt_password);
        confirmPassword = rootView.findViewById(R.id.edt_confirm_password);
        confirm = rootView.findViewById(R.id.btn_confirm);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                start(new RetrieveSuccessDelegate());
                break;
        }
    }
}
