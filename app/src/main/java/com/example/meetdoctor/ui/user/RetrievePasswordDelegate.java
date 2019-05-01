package com.example.meetdoctor.ui.user;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.utils.UIHelper;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class RetrievePasswordDelegate extends LatteDelegate implements
        View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    private EditText account, password, confirmPassword;
    private Button confirm;
    private ScrollView scrollView;

    @Override
    public Object setLayout() {
        return R.layout.delegate_retrieve_password;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        AppBarLayout appBarLayout = rootView.findViewById(R.id.app_bar);
        int statusBarHeight = UIHelper.getStatusBarHeight(getProxyActivity());
        appBarLayout.setPadding(0, statusBarHeight, 0, statusBarHeight);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setToolbar(toolbar);

        scrollView = rootView.findViewById(R.id.scroll_view);

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
    public void onSupportVisible() {
        super.onSupportVisible();
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                start(new RetrieveSuccessDelegate());
                break;
        }
    }

    @Override
    public void onGlobalLayout() {
        final View decorView = getProxyActivity().getWindow().getDecorView();
        UIHelper.setScrollViewHeight(decorView, scrollView);
    }
}
