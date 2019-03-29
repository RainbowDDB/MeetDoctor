package com.example.meetdoctor.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;

import com.example.meetdoctor.R;
import com.example.meetdoctor.adapter.PersonAdapter;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.model.FlagConstant;
import com.example.meetdoctor.model.bean.MemberListBean;
import com.example.meetdoctor.model.bean.PersonBean;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.widget.recycler.LatteRecyclerView;
import com.google.gson.Gson;

import java.util.List;
import java.util.WeakHashMap;

public class SwitchActivity extends SettingsBaseActivity {

    private static final String TAG = "SwitchActivity";
    private PersonAdapter adapter = null;
    private LatteRecyclerView recyclerView = null;

    private List<PersonBean> dataList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("切换对象");
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtils.getMemberList(this, response -> {
            MemberListBean memberListBean = new Gson().fromJson(response, MemberListBean.class);
            LatteLogger.json(TAG, response);
            dataList = memberListBean.getList();
            LatteLogger.d(dataList);
            // 进入此页面加载适配器，重新赋值
            adapter = new PersonAdapter(SwitchActivity.this,
                    dataList, memberListBean.getMemberId());
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    protected void initView() {

        Button addPerson = findViewById(R.id.btn_person_add);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        addPerson.setOnClickListener(view -> {
            WeakHashMap<String, Object> params = new WeakHashMap<>();
            params.put(FlagConstant.ADD_OR_EDIT, true);
//            EventBusUtils.postSticky(new EventMessage<>(EventCode.ADD_OR_EDIT, true));
            startActivity(EditActivity.class, params);
        });

    }

    @Override
    protected Object getLayout() {
        return R.layout.activity_switch;
    }
}
