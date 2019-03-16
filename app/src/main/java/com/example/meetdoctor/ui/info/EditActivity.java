package com.example.meetdoctor.ui.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.bean.PersonBean;
import com.example.meetdoctor.utils.UIHelper;

public class EditActivity extends BaseActivity {

    private EditText name;
    private EditText gender;
    private EditText age;
    private EditText birthday;
    private EditText note;

    private Button confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏颜色为黑
        UIHelper.setStatusBarColor(getWindow(), getResources().getColor(R.color.textBlack));
    }

    @Override
    protected void initView() {
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setPadding(0, UIHelper.getStatusBarHeight(this), 0, 0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        name = findViewById(R.id.edt_person_name);
        gender = findViewById(R.id.edt_person_gender); // TODO 性别改成 CheckBox
        age = findViewById(R.id.edt_person_age);
        birthday = findViewById(R.id.edt_person_birthday);
        note = findViewById(R.id.edt_person_note);

        confirm = findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(view -> {

        });

    }

    @Override
    protected Object getLayout() {
        return R.layout.activity_edit;
    }

    @Override
    public void onReceiveStickyEvent(EventMessage event) {
        super.onReceiveStickyEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            if (event.getData() instanceof PersonBean) {
                LatteLogger.d("hhhhhhhh");
            }
        }
    }
}
