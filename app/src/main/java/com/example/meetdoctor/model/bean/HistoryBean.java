package com.example.meetdoctor.model.bean;

public class HistoryBean {

    private String date;

    private int way;

    private String question;

    public HistoryBean(String date, int way, String question) {
        this.date = date;
        this.way = way;
        this.question = question;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
