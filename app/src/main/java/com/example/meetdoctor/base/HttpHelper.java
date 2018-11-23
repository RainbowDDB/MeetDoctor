package com.example.meetdoctor.base;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 网络操作助手类，封装OkHttp
 */
public class HttpHelper {

    private static final String BASE_URL = ".......";
    private static HttpHelper helper;
    private static OkHttpClient client;

    private HttpHelper() {
        client = new OkHttpClient();
    }

    public static HttpHelper getInstance() {
        if (helper == null) {
            synchronized (HttpHelper.class) {
                if (helper == null) {
                    helper = new HttpHelper();
                }
            }
        }
        return helper;
    }

    /**
     * 有参get请求
     *
     * @param url      url
     * @param params   参数键值对
     * @param callback 回调
     */
    public static void get(String url, LinkedHashMap<String, String> params, Callback callback) {
        String getUrl = attachHttpGetParams(BASE_URL + url, params);
        Request request = new Request.Builder()
                .url(getUrl)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 无参get请求
     *
     * @param url      url
     * @param callback 回调
     */
    public static void get(String url, Callback callback) {
        get(url, null, callback);
    }

    /**
     * post请求
     *
     * @param url      url
     * @param params   参数键值对
     * @param callback 回调
     */
    public static void post(String url, HashMap<String, String> params, Callback callback) {
        url = BASE_URL + url;
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 为HttpGet 的 url 方便的添加多个 name value 参数。
     *
     * @param url    url
     * @param params 参数键值对
     * @return get请求url
     */
    private static String attachHttpGetParams(String url, LinkedHashMap<String, String> params) {
        if (params != null && !params.isEmpty()) {
            Iterator<String> keys = params.keySet().iterator();
            Iterator<String> values = params.values().iterator();
            StringBuilder builder = new StringBuilder();
            builder.append("?");
            for (int i = 0; i < params.size(); i++) {
                String value = null;
                try {
                    value = URLEncoder.encode(values.next(), "utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                builder.append(keys.next()).append("=").append(value);
                if (i != params.size() - 1) {
                    builder.append("&");
                }
            }
            return url + builder.toString();
        } else {
            return url;
        }
    }
}
