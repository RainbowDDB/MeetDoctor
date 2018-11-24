package com.example.meetdoctor.utils;

import com.example.meetdoctor.base.HttpHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;

import okhttp3.Callback;

public class HttpUtils {

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码
     * @param callback 回调
     */
    public static void login(String userName, String password, Callback callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", SecurityUtils.md5(password));
        HttpHelper.getInstance().post("/user/login", params, callback);
    }

    /**
     * 注册
     *
     * @param userName 用户名
     * @param password 密码
     * @param callback 回调
     */
    public static void register(String userName, String password, Callback callback) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("username", userName);
        // 注册时密码无需md5加密
        params.put("password", password);
        HttpHelper.getInstance().get("/user/registered", params, callback);
    }

    /**
     * 判断用户是否已存在
     *
     * @param userName 用户名
     * @param callback 回调
     */
    public static void checkUser(String userName, Callback callback) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("username", userName);
        HttpHelper.getInstance().get("/user/check", params, callback);
    }
}
