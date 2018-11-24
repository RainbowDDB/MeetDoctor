package com.example.meetdoctor.model.event;

public class CheckUserEvent {

    private static final String[] MSG = {"用户已存在", "请求参数无效", "未知错误"};
    private int responseCode;

    public CheckUserEvent(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        switch (responseCode) {
            case 200:
                return "";
            case 400:
                return MSG[0];
            case 401:
                return MSG[1];
            default:
                return MSG[2];
        }
    }
}
