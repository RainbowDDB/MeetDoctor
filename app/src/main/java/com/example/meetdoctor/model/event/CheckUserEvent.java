package com.example.meetdoctor.model.event;

import com.example.meetdoctor.model.MessageConstant;

public class CheckUserEvent {

    private int responseCode;

    public CheckUserEvent(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        switch (responseCode) {
            case 200:
                return MessageConstant.USER_NAME_AVAILABLE;
            case 400:
                return MessageConstant.USER_EXISTED;
            case 401:
                return MessageConstant.PARAMS_UNAVAILABLE;
            default:
                return MessageConstant.UNKNOWN_ERROR;
        }
    }
}
