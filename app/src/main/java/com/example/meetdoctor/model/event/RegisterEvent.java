package com.example.meetdoctor.model.event;

import com.example.meetdoctor.model.MessageConstant;

/**
 * 注册成功进入此事件
 * Created By Rainbow on 2019/4/30.
 */
public class RegisterEvent {

    private String userName;
    private String password;

    public RegisterEvent(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return MessageConstant.REGISTER_SUCCESS;
    }
}
