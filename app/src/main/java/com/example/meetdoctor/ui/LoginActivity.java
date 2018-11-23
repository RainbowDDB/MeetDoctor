package com.example.meetdoctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView register, forgetPassword;
    private EditText account, password;
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
        account = findViewById(R.id.edt_account);
        password = findViewById(R.id.edt_password);
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
                break;
            case R.id.img_qq_login:
                break;
            case R.id.img_wechat_login:
                break;
            case R.id.img_other_login:
                break;
        }
    }
}
