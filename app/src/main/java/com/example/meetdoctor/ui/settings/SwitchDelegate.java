package com.example.meetdoctor.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.example.meetdoctor.R;
import com.example.meetdoctor.adapter.MemberAdapter;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.model.Constant;
import com.example.meetdoctor.model.bean.MemberListBean;
import com.example.meetdoctor.model.bean.MemberBean;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.widget.recycler.LatteRecyclerView;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class SwitchDelegate extends SettingsBaseDelegate {

    private static final String TAG = "SwitchActivity";
    private MemberAdapter adapter = null;
    private LatteRecyclerView recyclerView = null;

    private List<MemberBean> dataList = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_switch;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        setTitle("切换对象");

        Button addPerson = rootView.findViewById(R.id.btn_person_add);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getProxyActivity());
        recyclerView.setLayoutManager(layoutManager);

        addPerson.setOnClickListener(view -> {
            EditDelegate editDelegate = new EditDelegate();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constant.ADD_OR_EDIT, true);
            editDelegate.setArguments(bundle);
            start(editDelegate);
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

        HttpUtils.getMemberList(getProxyActivity(), response -> {
            MemberListBean memberListBean = new Gson().fromJson(response, MemberListBean.class);
            LatteLogger.json(TAG, response);
            dataList = memberListBean.getList();
            // 进入此页面加载适配器，重新赋值
            adapter = new MemberAdapter(getProxyActivity(), dataList, memberListBean.getMemberId());
            recyclerView.setAdapter(adapter);
        });
    }
}
