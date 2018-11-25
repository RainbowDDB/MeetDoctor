package com.example.meetdoctor.model.event;

import com.example.meetdoctor.model.bean.LoginBean;
import com.example.meetdoctor.model.MessageConstant;

public class LoginEvent {

    private int responseCode;
    private LoginBean data;

    public LoginEvent(int responseCode, LoginBean data) {
        this.responseCode = responseCode;
        this.data = data;
    }

    // 获取返回的实际信息
    public String getMessage() {
        switch (responseCode) {
            case 200:
                if (data.getResult() == 1) {
                    return MessageConstant.LOGIN_SUCCESS;
                } else {
                    if (data.getName() == 1) {
                        return MessageConstant.ACCOUNT_OR_PASSWORD_ERROR;
                    } else {
                        return MessageConstant.USER_INEXISTENT;
                    }
                }
            case 401:
                return MessageConstant.PARAMS_UNAVAILABLE;
            default:
                return MessageConstant.UNKNOWN_ERROR;
        }
    }
}
