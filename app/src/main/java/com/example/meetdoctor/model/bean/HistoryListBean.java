package com.example.meetdoctor.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class HistoryListBean {

    @SerializedName("u_a") // 用户回的话u，AI回的话a
    private String userOrAI;

    // 0为还没结束，1为已结束
    private int result;

    @SerializedName("words") // 诊断真实结果，其中包括病症名和治疗方案
    private Word word = null;

    // 返回是否为AI结果，即userOrAI.equals("a")
    public boolean isAI() {
        return userOrAI.equals("a");
    }

    public boolean isUser() {
        return userOrAI.equals("u");
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public class Word {

        private String name; // 病症名

        private String treatment; // 治疗方法

        @SerializedName("string")
        private String err; // result=0时返回

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

        public String getErr() {
            return err;
        }

        public void setErr(String err) {
            this.err = err;
        }
    }
}
