package com.example.meetdoctor.ui.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.meetdoctor.R;
import com.example.meetdoctor.adapter.HistoryAdapter;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.model.bean.HistoryBean;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.widget.Header;
import com.example.meetdoctor.widget.recycler.LatteRecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class HistoryDelegate extends LatteDelegate {

    private static final String TAG = "HistoryActivity";
    private HistoryAdapter adapter;
    private LatteRecyclerView recyclerView;

    @Override
    public Object setLayout() {
        return R.layout.activity_history;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Header header = rootView.findViewById(R.id.header);
        header.setTitle("历史记录");
        setToolbar(header.getToolbar());

        recyclerView = rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getProxyActivity());
        recyclerView.setLayoutManager(layoutManager);

        HttpUtils.getHistory(getProxyActivity(), response -> {
            LatteLogger.json(TAG, response);
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(response).getAsJsonArray();
            List<HistoryBean> historyList = new ArrayList<>();
            for (JsonElement json : array) {
                historyList.add(new Gson().fromJson(json, HistoryBean.class));
            }

            adapter = new HistoryAdapter(getProxyActivity(), historyList);
            recyclerView.setAdapter(adapter);
        });
    }
}
