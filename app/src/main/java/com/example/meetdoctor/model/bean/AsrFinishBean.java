package com.example.meetdoctor.model.bean;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class AsrFinishBean {
    private int error;
    private int sub_error;
    private String desc;
    private OriginResultBean origin_result;

    public int getError() {
        return error;
    }

    public int getSub_error() {
        return sub_error;
    }

    public String getDesc() {
        return desc;
    }

    public OriginResultBean getOrigin_result() {
        return origin_result;
    }
}
