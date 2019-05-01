package com.example.meetdoctor.model.event;

import com.example.meetdoctor.model.MessageConstant;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class LoginEvent {

    private int result;
    private int name;

    public LoginEvent(int result, int name) {
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

    public String getError() {
        if (result == 1) {
            // 登录成功
            return null;
        } else {
            if (name == 1) {
                return MessageConstant.ACCOUNT_OR_PASSWORD_ERROR;
            } else if (name == 0) {
                return MessageConstant.USER_INEXISTENT;
            } else {
                return MessageConstant.UNKNOWN_ERROR;
            }
        }
    }
}
