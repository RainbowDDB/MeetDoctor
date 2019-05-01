package com.example.meetdoctor.model.event;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class CheckStateEvent {

    private int state;

    private String[] list;

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
