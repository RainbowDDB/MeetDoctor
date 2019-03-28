package com.example.meetdoctor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.model.bean.HistoryBean;
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

        date.setText(bean.getDate());
        detail.setText(bean.getWay() == 0 ? "导诊：" : "问诊：" + bean.getQuestion());
//        detail.setText(MessageFormat.format("{0}:{1}", bean.getWay(), bean.getQuestion()));

        holder.itemView.setOnClickListener(view -> {
            // 点击操作
        });
    }
}
