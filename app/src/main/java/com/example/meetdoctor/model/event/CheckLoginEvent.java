package com.example.meetdoctor.model.event;

import com.example.meetdoctor.model.MessageConstant;

public class CheckLoginEvent {

    private int responseCode;

    public CheckLoginEvent(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        switch (responseCode) {
            case 200:
                return MessageConstant.LOGINED;
            case 406:
                return MessageConstant.NOT_LOGINED;
            default:
                throw new RuntimeException("ResponseCode is not right, please check.");
        }
    }
}
