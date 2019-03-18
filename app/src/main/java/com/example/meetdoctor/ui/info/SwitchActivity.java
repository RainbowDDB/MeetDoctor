package com.example.meetdoctor.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.meetdoctor.R;
import com.example.meetdoctor.adapter.PersonAdapter;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.core.net.callback.IError;
import com.example.meetdoctor.core.net.callback.ISuccess;
import com.example.meetdoctor.model.bean.MemberListBean;
import com.example.meetdoctor.model.bean.PersonBean;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.UIHelper;
import com.example.meetdoctor.widget.recyclerview.LatteRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SwitchActivity extends BaseActivity {

    private static final String TAG = "SwitchActivity";
    private PersonAdapter adapter;
    private Button addPerson;

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

        addPerson = findViewById(R.id.btn_person_add);

        LatteRecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        HttpUtils.getMemberList(this, response -> {
            MemberListBean memberListBean = new Gson().fromJson(response, MemberListBean.class);
            LatteLogger.json(TAG, response);
            List<PersonBean> list = memberListBean.getList();
            LatteLogger.d(list);
            adapter = new PersonAdapter(SwitchActivity.this, list);
            recyclerView.setAdapter(adapter);
        });

        addPerson.setOnClickListener(view -> {
            startActivity(EditActivity.class);
        });

    }

    @Override
    protected Object getLayout() {
        return R.layout.activity_switch;
    }
}
