package com.example.meetdoctor.model;

public class RegisterEvent {

    private static final String[] msg =
            new String[]{"注册成功", "用户已存在", "请求参数无效", "插入数据库发生意外错误,刷新重试", "未知错误"};
    private int responseCode;

    public RegisterEvent(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        switch (responseCode) {
            case 200:
                return msg[0];
            case 400:
                return msg[1];
            case 401:
                return msg[2];
            case 404:
                return msg[3];
            default:
                return msg[4];
        }
    }
}
