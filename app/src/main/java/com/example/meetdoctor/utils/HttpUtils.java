package com.example.meetdoctor.utils;

import android.content.Context;

import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.core.net.RestClient;
import com.example.meetdoctor.core.net.callback.IError;
import com.example.meetdoctor.core.net.callback.ISuccess;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.event.CheckUserEvent;
import com.example.meetdoctor.model.event.LoginEvent;
import com.example.meetdoctor.model.event.RegisterEvent;

import java.util.WeakHashMap;

public class HttpUtils {

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码
     */
    public static void login(Context context, String userName, String password) {
        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("username", userName);
        params.put("password", SecurityUtils.md5(password));
        RestClient.builder().url("user/login")
                .loader(context)
                .params(params)
                .success((response) -> {
                    if (response != null) {
                        LatteLogger.d("login success: code = " + 200 + ",responseData = " + response);
                        EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, new LoginEvent(200, response)));
                    }
                })
                .error((code, msg) -> {
                    LatteLogger.e("login error", "code=" + code + "  err=" + msg);
                    EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, new LoginEvent(code, msg)));
                })
                .failure(() -> EventBusUtils.post(new EventMessage(EventCode.NET_ERROR)))
                .build()
                .post();
//        HttpHelper.getInstance().post("/user/login", params, callback);
    }

    /**
     * 注册
     *
     * @param userName 用户名
     * @param password 密码
     */
    public static void register(Context context, String userName, String password) {
        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("username", userName);
        // 注册时密码无需md5加密
        params.put("password", password);
        RestClient.builder().url("user/registered")
                .loader(context)
                .params(params)
                .success((response) -> {
                    if (response != null) {
                        LatteLogger.d("register success: responseData=" + response);
                        EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, new RegisterEvent(200, userName, password)));
                    }
                })
                .error((code, msg) -> {
                    LatteLogger.e("register error", "code=" + code + "  err=" + msg);
                    EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, new RegisterEvent(code)));
                })
                .failure(() -> EventBusUtils.post(new EventMessage(EventCode.NET_ERROR)))
                .build()
                .get();
    }

    /**
     * 判断用户是否已存在
     *
     * @param userName 用户名
     */
    public static void checkUser(String userName) {
        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("username", userName);
        RestClient.builder().url("user/check")
                .params(params)
                .success((response) -> {
                    if (response != null) {
                        LatteLogger.d("checkUser success：responseData=" + response);
                        EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, new CheckUserEvent(200)));
                    }
                })
                .error((code, msg) -> {
                    LatteLogger.e("checkUser error", "code=" + code + "  err=" + msg);
                    EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, new CheckUserEvent(code)));
                })
                .failure(() -> EventBusUtils.post(new EventMessage(EventCode.NET_ERROR)))
                .build()
                .get();
//        HttpHelper.getInstance().get("/user/check", params, callback);
    }

    /**
     * 判断用户是否已登录，用cookie保存状态
     */
    public static void checkLogin(ISuccess iSuccess, IError iError) {
        RestClient.builder().url("user/checkLogin")
                .success(iSuccess)
                .error(iError)
                .failure(() -> EventBusUtils.post(new EventMessage(EventCode.NET_ERROR)))
                .build()
                .get();
//        HttpHelper.getInstance().get("/user/checkLogin", callback);
    }
}
