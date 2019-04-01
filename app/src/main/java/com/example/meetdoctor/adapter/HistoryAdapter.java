package com.example.meetdoctor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.model.bean.HistoryBean;
import com.example.meetdoctor.model.bean.HistoryListBean;
import com.example.meetdoctor.widget.recycler.BaseRecyclerViewAdapter;
import com.example.meetdoctor.widget.recycler.RecyclerViewHolder;

import java.util.List;

public class HistoryAdapter extends BaseRecyclerViewAdapter<HistoryBean> {

    public HistoryAdapter(Context context, List<HistoryBean> data) {
        super(context, data, R.layout.item_history);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, HistoryBean bean, int position) {
        View topLine = holder.getView(R.id.view_top_line);
        View bottomLine = holder.getView(R.id.view_bottom_line);

        TextView date = (TextView) holder.getView(R.id.tv_history_time);
        TextView detail = (TextView) holder.getView(R.id.tv_history_detail);

        if (position == 0) {
            topLine.setVisibility(View.GONE);
        }

        date.setText(bean.getTime());

//        bean.getHistoryList();
        for (HistoryListBean data : bean.getHistoryList()) {
            if (data.getResult() == 1) { // 判断是此次问询最后一次的结果，结束
                detail.setText(data.getWord().getName() + "\n" + data.getWord().getTreatment());
            }
        }

//        detail.setText(MessageFormat.format("{0}:{1}", bean.getWay(), bean.getQuestion()));

        holder.itemView.setOnClickListener(view -> {
            // 点击操作
        });
    }
}
