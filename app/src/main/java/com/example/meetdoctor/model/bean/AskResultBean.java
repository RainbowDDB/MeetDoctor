package com.example.meetdoctor.model.bean;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class AskResultBean {

    private int type;

    @SerializedName("success")
    private int code;

    @SerializedName("content")
    private ResultContent resultContent;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultContent getResultContent() {
        return resultContent;
    }

    public void setResultContent(ResultContent resultContent) {
        this.resultContent = resultContent;
    }

    public class ResultContent {
        String name; // 病症名

        String treatment;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTreatment() {
            return treatment;
        }

        public void setTreatment(String treatment) {
            this.treatment = treatment;
        }

        @NonNull
        @Override
        public String toString() {
            return "{" +
                    "name=" + name + "," +
                    "treatment=" + treatment +
                    "}";
        }
    }
}
