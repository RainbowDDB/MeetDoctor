package com.example.meetdoctor.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryBean {

    @SerializedName("list")
    private List<HistoryListBean> historyList = null;

    private String time;

    public List<HistoryListBean> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<HistoryListBean> historyList) {
        this.historyList = historyList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
