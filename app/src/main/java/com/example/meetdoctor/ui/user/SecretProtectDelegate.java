package com.example.meetdoctor.ui.user;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.utils.UIHelper;
import com.example.meetdoctor.widget.Header;

public class SecretProtectDelegate extends LatteDelegate implements
        View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    private Button nextStep;
    private EditText answer;
    private ScrollView scrollView;

    @Override
    public Object setLayout() {
        return R.layout.activity_secret_protect;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Header header = rootView.findViewById(R.id.header);
        header.setTitle("密保问题");
        setToolbar(header.getToolbar());

        scrollView = rootView.findViewById(R.id.scroll_view);

        nextStep = rootView.findViewById(R.id.btn_next_step);
        answer = rootView.findViewById(R.id.edt_secret_answer);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        nextStep.setOnClickListener(this);
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
            case R.id.btn_next_step:
                start(new RetrievePasswordDelegate());
                break;
        }
    }

    @Override
    public void onGlobalLayout() {
        final View decorView = getProxyActivity().getWindow().getDecorView();
        UIHelper.setScrollViewHeight(decorView, scrollView);
    }
}
