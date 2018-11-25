package com.example.meetdoctor.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.event.CheckUserEvent;
import com.example.meetdoctor.model.event.RegisterEvent;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    private EditText mAccount, mPassword, mConfirmedPassword;
    private CheckBox checkAgreement;
    private TextView agreement;
    private Button register;

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
        agreement = findViewById(R.id.tv_agreement);
        register = findViewById(R.id.btn_register_and_login);

        agreement.setOnClickListener(this);
        register.setOnClickListener(this);
        mAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                // 输入内容变化
                checkUser(c.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register_and_login:
                String password = mPassword.getText().toString();
                String confirmedPassword = mConfirmedPassword.getText().toString();
                if (password.equals(confirmedPassword)) {
                    // 两次输入密码相同
                    String userName = mAccount.getText().toString();
                    register(userName, password);
                } else {
                    // 提示重新输入
                    showToast("两次输入密码不一致，请重新输入！");
                }
                break;
            case R.id.tv_agreement:
                startActivity(AgreementActivity.class);
                break;
        }
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        super.onReceiveEvent(event);
        switch (event.getCode()) {
            case EventCode.SUCCESS:
                if (event.getData() instanceof RegisterEvent) {
                    showToast(((RegisterEvent) event.getData()).getMessage());
                } else if (event.getData() instanceof CheckUserEvent) {
                    String msg = ((CheckUserEvent) event.getData()).getMessage();
                    if (!msg.equals("")) {
                        showToast(msg);
                    } else {
                        showToast("用户名可用");
                    }
                }
                break;
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
    }
}
