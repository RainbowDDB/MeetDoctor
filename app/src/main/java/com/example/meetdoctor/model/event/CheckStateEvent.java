package com.example.meetdoctor.model.event;

import com.google.gson.Gson;

public class CheckStateEvent {

    private int state;

    private String[] list;

    public CheckStateEvent(String response) {
        CheckStateEvent bean = new Gson().fromJson(response, CheckStateEvent.class);
        state = bean.state;
        if (state == 1) {
            list = bean.list;
            // TODO 问用户是否继续之前问询
        } else {
            list = null;
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String[] getList() {
        return list;
    }

    public void setList(String[] list) {
        this.list = list;
    }
}
