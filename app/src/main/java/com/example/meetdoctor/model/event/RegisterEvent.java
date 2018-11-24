package com.example.meetdoctor.model.event;

import com.example.meetdoctor.model.MessageConstant;

public class RegisterEvent {

    private int responseCode;

    public RegisterEvent(int responseCode) {
        this.responseCode = responseCode;
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
                return MessageConstant.UNKNOWN_ERROR;
        }
    }
}
