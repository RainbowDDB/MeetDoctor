package com.example.meetdoctor.ui.user;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.MessageConstant;
import com.example.meetdoctor.model.event.LoginEvent;
import com.example.meetdoctor.model.event.RegisterEvent;
import com.example.meetdoctor.ui.HomeActivity;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.StringUtils;
import com.example.meetdoctor.utils.UIHelper;

public class RegisterActivity extends BaseActivity
        implements View.OnClickListener, TextView.OnEditorActionListener {

    @SuppressWarnings("unused")
    private static final String TAG = "RegisterActivity";
    private EditText mAccount, mPassword, mConfirmedPassword;
    private CheckBox checkAgreement;
    private TextView accountMsg, passwordMsg, confirmPasswordMsg;

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

        mAccount = findViewById(R.id.edt_account);
        mPassword = findViewById(R.id.edt_password);
        mConfirmedPassword = findViewById(R.id.edt_confirm_password);
        checkAgreement = findViewById(R.id.check_box_agreement);
        TextView agreement = findViewById(R.id.tv_agreement);
        Button register = findViewById(R.id.btn_register_and_login);
        accountMsg = findViewById(R.id.tv_user_name_msg);
        passwordMsg = findViewById(R.id.tv_password_msg);
        confirmPasswordMsg = findViewById(R.id.tv_confirm_password_msg);

        ScrollView scrollView = findViewById(R.id.scroll_view);
        UIHelper.setScrollViewHeight(getWindow(), scrollView);

        agreement.setOnClickListener(this);
        register.setOnClickListener(this);
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
    public Object getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register_and_login:
                startRegister();
                break;
            case R.id.tv_agreement:
                startActivity(AgreementActivity.class);
                break;
        }
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
                    HttpUtils.login(this, registerEvent.getUserName(), registerEvent.getPassword());
                } else if (event.getData() instanceof LoginEvent) {
                    LoginEvent bean = (LoginEvent) event.getData();
                    if (bean.getError() != null) {
                        showMessage(passwordMsg, bean.getError(), true);
                    } else {
                        showToast(MessageConstant.LOGIN_SUCCESS);
                        startNewActivity(HomeActivity.class);
                        finish();
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
        HttpUtils.register(this, userName, password);
    }

    // 检查用户是否存在
    private void checkUser(String userName) {
        if (userName.length() >= 6 && userName.length() <= 18 && StringUtils.isNumber(userName)) {
            HttpUtils.checkUser(userName);
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
