package com.example.meetdoctor.ui.user;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.MessageConstant;
import com.example.meetdoctor.model.event.LoginEvent;
import com.example.meetdoctor.model.event.RegisterEvent;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.StringUtils;
import com.example.meetdoctor.utils.UIHelper;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class RegisterDelegate extends LatteDelegate implements
        View.OnClickListener, TextView.OnEditorActionListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    @SuppressWarnings("unused")
    private static final String TAG = "RegisterActivity";
    private EditText mAccount, mPassword, mConfirmedPassword;
    private CheckBox checkAgreement;
    private TextView accountMsg, passwordMsg, confirmPasswordMsg;
    private ScrollView scrollView;

    // 密码输入观察者
    private TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // 动态监听
            String password = mPassword.getText().toString();
            String confirmPassword = mConfirmedPassword.getText().toString();
            if (checkPasswordLength(password)) {
                hideMessage(passwordMsg);
                if (!password.equals("")
                        && !confirmPassword.equals("")
                        && !checkPassword(password)) {
                    showMessage(confirmPasswordMsg, MessageConstant.PASSWORD_DIFFERENT, true);
                } else {
                    hideMessage(confirmPasswordMsg);
                }
            } else {
                showMessage(passwordMsg, MessageConstant.PASSWORD_ILLEGAL, true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public Object setLayout() {
        return R.layout.delegate_register;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        AppBarLayout appBarLayout = rootView.findViewById(R.id.app_bar);
        appBarLayout.setPadding(0, UIHelper.getStatusBarHeight(getProxyActivity()), 0, 0);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        getProxyActivity().setSupportActionBar(toolbar);
        ActionBar actionBar = getProxyActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mAccount = rootView.findViewById(R.id.edt_account);
        mPassword = rootView.findViewById(R.id.edt_password);
        mConfirmedPassword = rootView.findViewById(R.id.edt_confirm_password);
        checkAgreement = rootView.findViewById(R.id.check_box_agreement);
        TextView agreement = rootView.findViewById(R.id.tv_agreement);
        Button register = rootView.findViewById(R.id.btn_register_and_login);
        accountMsg = rootView.findViewById(R.id.tv_user_name_msg);
        passwordMsg = rootView.findViewById(R.id.tv_password_msg);
        confirmPasswordMsg = rootView.findViewById(R.id.tv_confirm_password_msg);

        scrollView = rootView.findViewById(R.id.scroll_view);
//        UIHelper.setScrollViewHeight(getProxyActivity().getWindow(), scrollView);

        agreement.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        mAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                // 输入内容变化
                if (!c.toString().equals("")) {
                    checkUser(c.toString());
                } else {
                    hideMessage(accountMsg);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mPassword.addTextChangedListener(passwordWatcher);
        mConfirmedPassword.addTextChangedListener(passwordWatcher);
        mConfirmedPassword.setOnEditorActionListener(this);
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
            case R.id.btn_register_and_login:
                startRegister();
                break;
            case R.id.tv_agreement:
                start(new AgreementDelegate());
                break;
        }
    }

    @Override
    public void onGlobalLayout() {
        final View decorView = getProxyActivity().getWindow().getDecorView();
        UIHelper.setScrollViewHeight(decorView, scrollView);
    }

    // 再次输入密码时，点击右下角done按键可直接注册
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch (i) {
            case EditorInfo.IME_ACTION_DONE:
                startRegister();
                break;
        }
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        super.onReceiveEvent(event);
        switch (event.getCode()) {
            case EventCode.USER_EXISTED:
                // 用户存在 400
                showMessage(accountMsg, MessageConstant.USER_EXISTED, true);
                break;
            case EventCode.SUCCESS:
                showMessage(accountMsg, MessageConstant.USER_NAME_AVAILABLE, false);
                if (event.getData() instanceof RegisterEvent) {
                    RegisterEvent registerEvent = (RegisterEvent) event.getData();
                    showToast(registerEvent.getMessage());
                    // 自动登录
                    HttpUtils.login(getProxyActivity(),
                            registerEvent.getUserName(),
                            registerEvent.getPassword());
                } else if (event.getData() instanceof LoginEvent) {
                    LoginEvent bean = (LoginEvent) event.getData();
                    // 这里仅提示错误信息，剩下交给LoginDelegate中的event管理
                    if (bean.getError() != null) {
                        showMessage(passwordMsg, bean.getError(), true);
                    }
                }
                break;
        }
    }

    // 注册之前的各种判断
    private void startRegister() {
        hideAllMessage();
        String password = mPassword.getText().toString();
        // 同意协议
        if (checkAgreement()) {
            if (checkPasswordLength(password)) {
                // 两次输入密码相同
                if (checkPassword(password)) {
                    String userName = mAccount.getText().toString();
                    register(userName, password);
                } else {
                    // 提示重新输入
                    showMessage(confirmPasswordMsg, MessageConstant.PASSWORD_DIFFERENT, true);
                }
            } else {
                showMessage(confirmPasswordMsg, MessageConstant.PASSWORD_ILLEGAL, true);
            }
        } else {
            // 未同意协议
            showMessage(confirmPasswordMsg, MessageConstant.AGREEMENT_DISAGREE, true);
        }
    }

    // 注册
    private void register(String userName, String password) {
        HttpUtils.register(getProxyActivity(), userName, password);
    }

    // 检查用户是否存在
    private void checkUser(String userName) {
        if (userName.length() >= 6 && userName.length() <= 18 && StringUtils.isNumber(userName)) {
            HttpUtils.checkUser(getProxyActivity(), userName);
        } else {
            showMessage(accountMsg, MessageConstant.USER_NAME_ILLEGAL, true);
        }
    }

    private boolean checkPassword(String password) {
        String confirmedPassword = mConfirmedPassword.getText().toString();
        return password.equals(confirmedPassword);
    }

    private boolean checkPasswordLength(String password) {
        return password.length() <= 18;
    }

    private boolean checkAgreement() {
        return checkAgreement.isChecked();
    }

    // 显示错误提示
    private void showMessage(TextView textView, String msg, boolean isError) {
        if (textView != null) {
            if (isError) {
                textView.setTextColor(getResources().getColor(R.color.warning_red));
                textView.setText(msg);
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setTextColor(getResources().getColor(R.color.green));
                textView.setText(msg);
                textView.setVisibility(View.VISIBLE);
            }
        }
    }

    // 隐藏
    private void hideMessage(TextView textView) {
        textView.setVisibility(View.INVISIBLE);
    }

    private void hideAllMessage() {
        hideMessage(accountMsg);
        hideMessage(passwordMsg);
        hideMessage(confirmPasswordMsg);
    }
}
