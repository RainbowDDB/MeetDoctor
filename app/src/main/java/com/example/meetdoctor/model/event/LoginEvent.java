package com.example.meetdoctor.model.event;

import com.example.meetdoctor.model.LoginBean;

public class LoginEvent {

    private static final String[] msg =
            new String[]{"登陆成功", "账号或者密码错误", "账户不存在", "请求无效参数", "未知错误"};

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
                    return msg[0];
                } else {
                    if (data.getName() == 1) {
                        return msg[1];
                    } else {
                        return msg[2];
                    }
                }
            case 401:
                return msg[3];
            default:
                return msg[4];
        }
    }
}
