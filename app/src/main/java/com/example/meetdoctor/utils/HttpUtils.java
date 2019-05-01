package com.example.meetdoctor.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.core.net.RestClient;
import com.example.meetdoctor.core.net.RestClientBuilder;
import com.example.meetdoctor.core.net.callback.IError;
import com.example.meetdoctor.core.net.callback.ISuccess;
import com.example.meetdoctor.model.Constant;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.event.LoginEvent;
import com.example.meetdoctor.model.event.RegisterEvent;
import com.google.gson.Gson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.WeakHashMap;

/**
 * 网络工具操作类，只是一些项目需求的接口
 * Context 建议只使用 <b>getProxyActivity()</b>方法获取
 * Created By Rainbow on 2019/4/30.
 */
public class HttpUtils {

    private static final String TAG = "HttpUtils";

    /**
     * 问询
     *
     * @param type     类型，参照Constant.AskType说明
     * @param question 问题，当type为NORMAL_ASK或RE_ASK时为null，不传此参数
     * @param response 回答
     * @param iSuccess 成功的回调
     */
    public static void ask(Context context,
                           @AskMode int type,
                           String question,
                           String response,
                           ISuccess iSuccess) {
        RestClientBuilder builder = RestClient.builder()
                .url("ask/answer")
                .params("type", type)
                .params("w", response)
                .success(iSuccess)
                .error(new Error((Activity) context));
        switch (type) {
            case Constant.AskType.LEVEL_ASK:
            case Constant.AskType.SELECTION_ASK:
                builder.params("question", question).build().post();
                break;
            case Constant.AskType.NORMAL_ASK:
            case Constant.AskType.RE_ASK:
                builder.build().post();
                break;
            default:
                throw new IllegalArgumentException("type is not right.");
        }
    }

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
                        EventBusUtils.post((Activity) context,
                                new EventMessage<>(EventCode.SUCCESS, bean));
                    }
                })
                .error(new Error((Activity) context))
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
                        EventBusUtils.post((Activity) context, new EventMessage<>(
                                EventCode.SUCCESS,
                                new RegisterEvent(userName, password)));
                    }
                })
                .error(new Error((Activity) context))
                .build()
                .get();
    }

    /**
     * 判断用户是否已存在
     *
     * @param userName 用户名
     */
    public static void checkUser(Context context, String userName) {
        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("username", userName);
        RestClient.builder().url("user/check")
                .params(params)
                .success((response) -> {
                    if (response != null) {
                        LatteLogger.d("checkUser success：responseData=" + response);
                        EventBusUtils.post((Activity) context, new EventMessage<>(EventCode.SUCCESS));
                    }
                })
                .error(new Error((Activity) context))
                .build()
                .get();
    }

    public static void checkState(Context context, ISuccess iSuccess) {
        RestClient.builder().url("ask/state")
                .success(iSuccess)
                .error(new Error((Activity) context))
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

    /**
     * 获取对象列表
     */
    public static void getMemberList(Context context, ISuccess iSuccess) {
        RestClient.builder().url("person/GetMemberList")
                .loader(context)
                .success(iSuccess)
                .error(new Error((Activity) context))
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
        // 在Retrofit的post方法中
        // 传入的 Field Map 的 key 和 value 均不能为null，否则会返回在onFailure中
        map.put("height", height != null ? height : "");
        map.put("weight", weight != null ? weight : "");
        map.put("birthday", birthday);
        RestClient.builder().url("person/CreateMember")
                .success(iSuccess)
                .error(new Error((Activity) context))
                .loader(context)
                .params(map)
                .build()
                .post();
    }

    /**
     * 切换对象
     */
    public static void switchMember(Context context, int memberId, ISuccess iSuccess) {
        RestClient.builder().url("person/ChangeMember")
                .success(iSuccess)
                .error(new Error((Activity) context))
                .params("object", memberId)
                .build()
                .post();
    }

    /**
     * 编辑对象
     */
    public static void modifyMember(Context context,
                                    int memberId,
                                    String name,
                                    String alias,
                                    int gender,   // 1男，0女
                                    @Nullable Double height,
                                    @Nullable Double weight,
                                    String birthday,  // YYYY-MM-DD
                                    ISuccess iSuccess) {
        if (name.equals("")) {
            throw new IllegalArgumentException("name is not empty!");
        }
        WeakHashMap<String, Object> map = new WeakHashMap<>();
        map.put("member_id", memberId);
        map.put("name", name); // name不能为""
        map.put("alias", alias);
        map.put("sex", gender);
        // 在Retrofit的post方法中
        // 传入的 Field Map 的 key 和 value 均不能为null，否则会返回在onFailure中
        map.put("height", height != null ? height : "");
        map.put("weight", weight != null ? weight : "");
        map.put("birthday", birthday);

        RestClient.builder().url("person/ModifyMember")
                .success(iSuccess)
                .error(new Error((Activity) context))
                .loader(context)
                .params(map)
                .build()
                .post();
    }

    public static void ask(Context context, @AskMode int type, String response, ISuccess iSuccess) {
        ask(context, type, null, response, iSuccess);
    }

    @IntDef({Constant.AskType.NORMAL_ASK,
            Constant.AskType.LEVEL_ASK,
            Constant.AskType.SELECTION_ASK,
            Constant.AskType.RE_ASK})
    @Retention(RetentionPolicy.SOURCE)
    @interface AskMode {
    }

    /**
     * 加载历史
     */
    public static void getHistory(Context context, ISuccess iSuccess) {
        RestClient.builder().url("person/LoadHistory")
                .loader(context)
                .success(iSuccess)
                .error(new Error((Activity) context))
                .build()
                .get();
    }

    private static final class Error implements IError {

        private final Activity mActivity;

        private Error(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        public void onError(int code, String msg) {
            LatteLogger.e(TAG, code + "   " + msg);
            EventBusUtils.post(mActivity, new EventMessage<>(code, msg));
        }
    }
}
