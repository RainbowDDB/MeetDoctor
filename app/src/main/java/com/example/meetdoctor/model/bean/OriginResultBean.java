package com.example.meetdoctor.model.bean;

public class OriginResultBean {

    private String corpus_no;
    private String err_no;
    private String sn;
    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public String getCorpus_no() {
        return corpus_no;
    }

    public String getErr_no() {
        return err_no;
    }

    public String getSn() {
        return sn;
    }
}