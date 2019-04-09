package com.example.meetdoctor.ui.user;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.MessageConstant;
import com.example.meetdoctor.model.event.LoginEvent;
import com.example.meetdoctor.ui.HomeDelegate;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.UIHelper;

public class LoginDelegate extends LatteDelegate
        implements View.OnClickListener, TextView.OnEditorActionListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    @SuppressWarnings("unused")
    private static final String TAG = "LoginActivity";
    private EditText mAccount, mPassword;
    private TextView errMsg;
    private ScrollView scrollView;

    private boolean isHidden = false; // 判断是否已经打开RegisterDelegate

    @Override
    public Object setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        TextView register = rootView.findViewById(R.id.tv_register);
        TextView forgetPassword = rootView.findViewById(R.id.tv_forget_password);
        mAccount = rootView.findViewById(R.id.edt_account);
        mPassword = rootView.findViewById(R.id.edt_password);
        Button login = rootView.findViewById(R.id.btn_login);
        ImageView qqLogin = rootView.findViewById(R.id.img_qq_login);
        ImageView wechatLogin = rootView.findViewById(R.id.img_wechat_login);
        ImageView otherLogin = rootView.findViewById(R.id.img_other_login);
        errMsg = rootView.findViewById(R.id.tv_error_msg);

        scrollView = rootView.findViewById(R.id.scroll_view);

        register.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        login.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        wechatLogin.setOnClickListener(this);
        otherLogin.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 只要输入即清除错误信息
                hideErrorMessage(errMsg);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        mAccount.addTextChangedListener(watcher);
        mPassword.addTextChangedListener(watcher);
        mPassword.setOnEditorActionListener(this);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当此碎片可见时注册
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        isHidden = false; // 恢复isHidden状态，重点
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 不可见时就将其remove，这样可尽可能避免Memory Leak
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                start(new RegisterDelegate());
                isHidden = true;
                break;
            case R.id.tv_forget_password:
                start(new SecretProtectDelegate());
                break;
            case R.id.btn_login:
//                HttpUtils.login(this, mAccount.getText().toString(), mPassword.getText().toString());
                login();
                break;
            case R.id.img_qq_login:
                break;
            case R.id.img_wechat_login:
                break;
            case R.id.img_other_login:
                break;
        }

    }

    @Override
    public void onGlobalLayout() {
        // 监听软键盘，屏幕高度以改变ScrollView的高度
        final View decorView = getProxyActivity().getWindow().getDecorView();
        UIHelper.setScrollViewHeight(decorView, scrollView);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch (i) {
            case EditorInfo.IME_ACTION_DONE:
                login();
                break;
        }
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        super.onReceiveEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            if (event.getData() instanceof LoginEvent) {
                LoginEvent bean = (LoginEvent) event.getData();
                if (bean.getResult() == 1) {
                    // 登录成功
                    hideErrorMessage(errMsg);
                    showToast(MessageConstant.LOGIN_SUCCESS);
                    // 在这里也同时判断来自RegisterDelegate的消息，如果成功登陆，直接跳转
                    startWithPopTo(new HomeDelegate(), LoginDelegate.class, true);
                } else {
                    if (!isHidden) { // 只是在Login界面登陆才会提示错误信息
                        showErrorMessage(errMsg, bean.getError());
                    }
                }
            }
        }
    }

    private void login() {
        String userName = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        if (!userName.isEmpty() && !password.isEmpty()) {
            HttpUtils.login(getProxyActivity(), userName, password);
        }
    }

    private void showErrorMessage(TextView tv, String msg) {
        tv.setText(msg);
        tv.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage(TextView tv) {
        tv.setVisibility(View.INVISIBLE);
    }
}
