package com.example.meetdoctor.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MemberListBean {

    private ArrayList<MemberBean> list;

    @SerializedName("chosen_id")
    private int memberId;

    public ArrayList<MemberBean> getList() {
        return list;
    }

    public void setList(ArrayList<MemberBean> list) {
        this.list = list;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
