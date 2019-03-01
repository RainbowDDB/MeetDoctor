package com.example.meetdoctor.model.event;

import com.example.meetdoctor.model.bean.LoginBean;
import com.example.meetdoctor.model.MessageConstant;
import com.google.gson.Gson;

public class LoginEvent {

    private int responseCode;
    private LoginBean data;

    public LoginEvent(int responseCode, String data) {
        this.responseCode = responseCode;
        if (responseCode == 200) {
            this.data = new Gson().fromJson(data, LoginBean.class);
        } else {
            this.data = null;
        }
    }

    // 获取返回的实际信息
    private String getMessage() {
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
                throw new RuntimeException("ResponseCode is not right, please check.");
        }
    }

    // 获取错误信息
    public String getError() {
        if (responseCode == 200 && data.getResult() == 1) {
            return "";
        } else {
            return getMessage();
        }
    }
}
