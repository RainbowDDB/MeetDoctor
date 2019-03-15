package com.example.meetdoctor.model.bean;

import java.util.ArrayList;

public class AsrPartialBean {
    private ArrayList<String> results_recognition;
    private OriginResultBean origin_result;
    private String error;
    private String best_result;
    private String result_type;

    public ArrayList<String> getResults_recognition() {
        return results_recognition;
    }

    public OriginResultBean getOrigin_result() {
        return origin_result;
    }

    public String getBest_result() {
        return best_result;
    }

    public String getError() {
        return error;
    }

    public String getResult_type() {
        return result_type;
    }

}
