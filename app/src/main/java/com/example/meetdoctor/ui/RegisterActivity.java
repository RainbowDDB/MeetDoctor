package com.example.meetdoctor.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.MessageConstant;
import com.example.meetdoctor.model.event.CheckUserEvent;
import com.example.meetdoctor.model.event.RegisterEvent;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.StringUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity
        implements View.OnClickListener, TextView.OnEditorActionListener {

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
    }

    @Override
    public int getLayoutId() {
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
            case EventCode.SUCCESS:
                if (event.getData() instanceof RegisterEvent) {
                    String msg = ((RegisterEvent) event.getData()).getMessage();
                    if (!msg.equals(MessageConstant.REGISTER_SUCCESS)) {
                        showMessage(passwordMsg, msg, true);
                    } else {
                        // TODO 注册成功，显示模态弹窗
                        showToast(msg);
                    }
                } else if (event.getData() instanceof CheckUserEvent) {
                    String msg = ((CheckUserEvent) event.getData()).getMessage();
                    if (!msg.equals(MessageConstant.USER_NAME_AVAILABLE)) {
                        showMessage(accountMsg, msg, true);
                    } else {
                        showMessage(accountMsg, msg, false);
                    }
                }
                break;
            case EventCode.USER_NAME_ILLEGAL:
                showMessage(accountMsg, MessageConstant.USER_NAME_ILLEGAL, true);
                break;
        }
    }

    // 注册之前的各种判断
    private void startRegister() {
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
        HttpUtils.register(userName, password, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, e.toString());
                EventBusUtils.post(new EventMessage(EventCode.NET_ERROR));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    int code = response.code();
                    String responseData = response.body().string();
                    Log.d(TAG, "code=" + code + "   responseData=" + responseData);
                    EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, new RegisterEvent(code)));
                }
            }
        });
    }

    // 检查用户是否存在
    private void checkUser(String userName) {
        if (userName.length() >= 6 && userName.length() <= 18 && StringUtils.isNumber(userName)) {
            HttpUtils.checkUser(userName, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e(TAG, e.toString());
                    EventBusUtils.post(new EventMessage(EventCode.NET_ERROR));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.body() != null) {
                        int code = response.code();
                        String responseData = response.body().string();
                        Log.d(TAG, "code=" + code + "   responseData=" + responseData);
                        EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, new CheckUserEvent(code)));
                    }
                }
            });
        } else {
            EventBusUtils.post(new EventMessage<>(EventCode.USER_NAME_ILLEGAL));
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
}
