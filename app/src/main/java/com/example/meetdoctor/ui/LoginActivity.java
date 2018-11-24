package com.example.meetdoctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.LoginBean;
import com.example.meetdoctor.model.event.LoginEvent;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private TextView register, forgetPassword;
    private EditText mAccount, mPassword;
    private Button login;
    private ImageView qqLogin, wechatLogin, otherLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        register = findViewById(R.id.tv_register);
        forgetPassword = findViewById(R.id.tv_forget_password);
        mAccount = findViewById(R.id.edt_account);
        mPassword = findViewById(R.id.edt_password);
        login = findViewById(R.id.btn_login);
        qqLogin = findViewById(R.id.img_qq_login);
        wechatLogin = findViewById(R.id.img_wechat_login);
        otherLogin = findViewById(R.id.img_other_login);

        register.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        login.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        wechatLogin.setOnClickListener(this);
        otherLogin.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_forget_password:
                startActivity(SecretProtectActivity.class);
                break;
            case R.id.btn_login:
                String userName = mAccount.getText().toString();
                String password = mPassword.getText().toString();
                login(userName, password);
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
    public void onReceiveEvent(EventMessage event) {
        super.onReceiveEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            showToast(((LoginEvent) event.getData()).getMessage());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void login(String userName, String password) {
        HttpUtils.login(userName, password, new Callback() {
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
                    Gson gson = new Gson();
                    LoginBean bean = gson.fromJson(responseData, LoginBean.class);
                    EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, new LoginEvent(code, bean)));
                }
            }
        });
    }
}