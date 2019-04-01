package com.example.meetdoctor.ui.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;

import com.example.meetdoctor.R;
import com.example.meetdoctor.adapter.HistoryAdapter;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.core.net.callback.ISuccess;
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

public class HistoryActivity extends BaseActivity {

    private static final String TAG = "HistoryActivity";
    private HistoryAdapter adapter;
    private LatteRecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Header header = findViewById(R.id.header);
        header.setTitle("历史记录");
        setSupportActionBar(header.getToolbar());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<HistoryBean> data = new ArrayList<>();
//        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
//        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
//        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
//        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
//        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
//        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
//        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
//        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));

//        adapter = new HistoryAdapter(this, data);

//        recyclerView.setAdapter(adapter);

        HttpUtils.getHistory(this, new ISuccess() {
            @Override
            public void onSuccess(String response) {
                LatteLogger.json(TAG, response);
                JsonParser parser = new JsonParser();
                JsonArray array = parser.parse(response).getAsJsonArray();
                List<HistoryBean> historyList = new ArrayList<>();
                for (JsonElement json : array) {
                    historyList.add(new Gson().fromJson(json, HistoryBean.class));
                }

                adapter = new HistoryAdapter(HistoryActivity.this, historyList);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected Object getLayout() {
        return R.layout.activity_history;
    }
}
