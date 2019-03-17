package com.example.meetdoctor.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MemberListBean {

    private ArrayList<PersonBean> list;

    @SerializedName("chosen_id")
    private int memberId;

    public ArrayList<PersonBean> getList() {
        return list;
    }

    public void setList(ArrayList<PersonBean> list) {
        this.list = list;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
