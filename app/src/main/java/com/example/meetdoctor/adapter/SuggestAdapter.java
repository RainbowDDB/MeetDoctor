package com.example.meetdoctor.adapter;

import android.content.Context;
import android.content.res.Resources;

import com.example.meetdoctor.R;
import com.example.meetdoctor.model.bean.SuggestBean;
import com.example.meetdoctor.widget.recycler.BaseRecyclerViewAdapter;
import com.example.meetdoctor.widget.recycler.RecyclerViewHolder;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class SuggestAdapter extends BaseRecyclerViewAdapter<SuggestBean> {
    public SuggestAdapter(Context context, List<SuggestBean> data) {
        super(context, data, R.layout.item_suggest_info);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, SuggestBean bean, int position) {
        PieChart chart = (PieChart) holder.getView(R.id.pie_chart_suggest);

        List<Float> list = new ArrayList<>();
        list.add(70.1f);
        list.add(29.9f);
        setPieChart(chart, list);

        chart.invalidate();
    }

    private void setPieChart(final PieChart pieChart, List<Float> dataSet) {
//        pieChart.setUsePercentValues(true); // 百分比显示
        pieChart.setDrawCenterText(false); // 中心不显示字体
        pieChart.setDrawHoleEnabled(false); // 中间不掏空 饼图
        pieChart.getLegend().setEnabled(false); // 不显示图例
//        pieChart.getDescription().setEnabled(false); // 不设置描述
        pieChart.animate(); // 数据显示动画

//        pieChart.setUsePercentValues(true);//设置使用百分比（后续有详细介绍）

//        pieChart.setExtraOffsets(25, 10, 25, 25); //设置边距
//        pieChart.setDragDecelerationFrictionCoef(0.95f);//设置摩擦系数（值越小摩擦系数越大）
//        pieChart.setRotationEnabled(true);//是否可以旋转
//        pieChart.setHighlightPerTapEnabled(true);//点击是否放大
//        pieChart.setCenterTextSize(22f);//设置环中文字的大小

//        pieChart.setRotationAngle(120f);//设置旋转角度
//        pieChart.setTransparentCircleRadius(61f);//设置半透明圆环的半径,看着就有一种立体的感觉
//        //设置半透明圆环的颜色
//        pieChart.setTransparentCircleColor(Color.WHITE);
//        //设置半透明圆环的透明度
//        pieChart.setTransparentCircleAlpha(110);

        Resources res = getContext().getResources();
        List<PieEntry> pieEntries = getPieData(dataSet);
        PieData pieData = new PieData();
        pieData.setValueTextColor(res.getColor(R.color.textBlack));
//        pieData.setValueFormatter(new PercentFormatter()); // 百分比

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        final int[] PIE_COLORS = {res.getColor(R.color.colorPrimary), res.getColor(R.color.colorPrimaryDark)};
        pieDataSet.setColors(PIE_COLORS);
        pieDataSet.setValueTextSize(11f);

        pieData.setDataSet(pieDataSet);
        pieChart.setData(pieData);
    }

    private List<PieEntry> getPieData(List<Float> list) {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (float data : list) {
            pieEntries.add(new PieEntry(data, "f"));
        }
        return pieEntries;
    }
}
