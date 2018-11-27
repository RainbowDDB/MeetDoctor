package com.example.meetdoctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.MessageConstant;
import com.example.meetdoctor.model.bean.LoginBean;
import com.example.meetdoctor.model.event.LoginEvent;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.UIHelper;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseActivity
        implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String TAG = "LoginActivity";
    private EditText mAccount, mPassword;
    private TextView errMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        TextView register = findViewById(R.id.tv_register);
        TextView forgetPassword = findViewById(R.id.tv_forget_password);
        mAccount = findViewById(R.id.edt_account);
        mPassword = findViewById(R.id.edt_password);
        Button login = findViewById(R.id.btn_login);
        ImageView qqLogin = findViewById(R.id.img_qq_login);
        ImageView wechatLogin = findViewById(R.id.img_wechat_login);
        ImageView otherLogin = findViewById(R.id.img_other_login);
        errMsg = findViewById(R.id.tv_error_msg);

        ScrollView scrollView = findViewById(R.id.scroll_view);
        // 监听软键盘状态从而改变ScrollView高度
        UIHelper.setScrollViewHeight(getWindow(), scrollView);

        register.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        login.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        wechatLogin.setOnClickListener(this);
        otherLogin.setOnClickListener(this);

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
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch (i) {
            case EditorInfo.IME_ACTION_DONE:
                String userName = mAccount.getText().toString();
                String password = mPassword.getText().toString();
                login(userName, password);
                break;
        }
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        super.onReceiveEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            String err = ((LoginEvent) event.getData()).getError();
            if (!err.equals("")) {
                showErrorMessage(errMsg, err);
            } else {
                hideErrorMessage(errMsg);
                showToast(MessageConstant.LOGIN_SUCCESS);
            }
        }
    }

    // 忘记密码修改成功后返回调用
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

    private void showErrorMessage(TextView tv, String msg) {
        tv.setText(msg);
        tv.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage(TextView tv) {
        tv.setVisibility(View.INVISIBLE);
    }
}
