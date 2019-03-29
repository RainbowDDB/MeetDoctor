package com.example.meetdoctor.ui.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;

import com.example.meetdoctor.R;
import com.example.meetdoctor.adapter.HistoryAdapter;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.model.bean.HistoryBean;
import com.example.meetdoctor.widget.Header;
import com.example.meetdoctor.widget.recycler.LatteRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity {

    private HistoryAdapter adapter;

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
    protected void initView() {
        LatteRecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<HistoryBean> data = new ArrayList<>();
        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));
        data.add(new HistoryBean("2019-3-4", 1, "肚子疼咋办？"));

        adapter = new HistoryAdapter(this, data);

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Object getLayout() {
        return R.layout.activity_history;
    }
}
