package com.example.meetdoctor.ui.settings;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;

import com.example.meetdoctor.R;
import com.example.meetdoctor.adapter.PersonAdapter;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.model.bean.MemberListBean;
import com.example.meetdoctor.model.bean.PersonBean;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.widget.recyclerview.LatteRecyclerView;
import com.google.gson.Gson;

import java.util.List;

public class SwitchActivity extends SettingsBaseActivity {

    private static final String TAG = "SwitchActivity";
    private PersonAdapter adapter;
    private Button addPerson;

    @Override
    protected void initView() {
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
