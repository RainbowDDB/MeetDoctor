package com.example.meetdoctor.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.core.net.RestClient;
import com.example.meetdoctor.core.net.callback.IError;
import com.example.meetdoctor.core.net.callback.ISuccess;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.event.LoginEvent;
import com.example.meetdoctor.model.event.RegisterEvent;
import com.google.gson.Gson;

import java.util.WeakHashMap;

public class HttpUtils {

    private static final String TAG = "HttpUtils";

    private static IError ERROR = (code, msg) -> {
        LatteLogger.e(TAG, code + "   " + msg);
        EventBusUtils.post(new EventMessage<>(code, msg));
    };

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
                        LatteLogger.d("login success: responseData = " + response);
                        LoginEvent bean = new Gson().fromJson(response, LoginEvent.class);
                        EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, bean));
                    }
                })
                .error(ERROR)
                .build()
                .post();
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
                        EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS, new RegisterEvent(userName, password)));
                    }
                })
                .error(ERROR)
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
                        EventBusUtils.post(new EventMessage<>(EventCode.SUCCESS));
                    }
                })
                .error(ERROR)
                .build()
                .get();
    }

    /**
     * 判断用户是否已登录，用cookie保存状态
     */
    public static void checkLogin(ISuccess iSuccess, IError iError) {
        RestClient.builder().url("user/checklogin")
                .success(iSuccess)
                .error(iError)
                .build()
                .get();
    }

    public static void checkState(ISuccess iSuccess) {
        RestClient.builder().url("ask/state")
                .success(iSuccess)
                .error(ERROR)
                .build()
                .get();
    }

    /**
     * 获取对象列表
     */
    public static void getMemberList(Context context, ISuccess iSuccess) {
        RestClient.builder().url("person/GetMemberList")
                .loader(context)
                .success(iSuccess)
                .error(ERROR)
                .build()
                .get();
    }

    /**
     * 创建对象
     */
    public static void createMember(Context context,
                                    String name,
                                    String alias,
                                    int gender,   // 1男，0女
                                    @Nullable Double height,
                                    @Nullable Double weight,
                                    String birthday,  // YYYY-MM-DD
                                    ISuccess iSuccess) {
        WeakHashMap<String, Object> map = new WeakHashMap<>();
        map.put("name", name);
        map.put("alias", alias);
        map.put("sex", gender);
        map.put("height", height);
        map.put("weight", weight);
        map.put("birthday", birthday);
        RestClient.builder().url("person/CreateMember")
                .success(iSuccess)
                .error(ERROR)
                .loader(context)
                .params(map)
                .build()
                .post();
    }
}
