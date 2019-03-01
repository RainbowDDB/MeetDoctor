package com.example.meetdoctor.model.event;

import com.example.meetdoctor.model.MessageConstant;

public class RegisterEvent {

    private int responseCode;
    private String userName;
    private String password;

    public RegisterEvent(int responseCode, String userName, String password) {
        this.responseCode = responseCode;
        this.userName = userName;
        this.password = password;
    }

    public RegisterEvent(int responseCode) {
        this(responseCode, null, null);
    }

    // 获取返回的实际信息
    public String getMessage() {
        switch (responseCode) {
            case 200:
                return MessageConstant.REGISTER_SUCCESS;
            case 400:
                return MessageConstant.USER_EXISTED;
            case 401:
                return MessageConstant.PARAMS_UNAVAILABLE;
            case 404:
                return MessageConstant.DATABASE_ERROR;
            default:
                throw new RuntimeException("ResponseCode is not right, please check.");
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
