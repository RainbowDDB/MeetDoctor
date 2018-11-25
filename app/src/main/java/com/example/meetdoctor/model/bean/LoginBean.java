package com.example.meetdoctor.model.bean;

public class LoginBean {

    private int result;
    private int name;

    public LoginBean(int result, int name) {
        this.result = result;
        this.name = name;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
