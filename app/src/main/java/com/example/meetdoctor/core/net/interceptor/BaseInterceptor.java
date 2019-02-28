package com.example.meetdoctor.core.net.interceptor;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

public abstract class BaseInterceptor implements Interceptor {
    /**
     * 获得url参数对
     *
     * @param chain chain
     * @return 参数对
     */
    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        final HttpUrl url = chain.request().url();
        int size = url.querySize();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return params;
    }

    protected String getUrlParameter(Chain chain, String key) {
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    /**
     * 获取请求体参数对
     *
     * @param chain chain
     * @return 参数对
     */
    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        final FormBody body = (FormBody) chain.request().body();
        int size;
        if (body != null) {
            size = body.size();
            final LinkedHashMap<String, String> params = new LinkedHashMap<>();
            for (int i = 0; i < size; i++) {
                params.put(body.name(i), body.value(i));
            }
            return params;
        } else {
            throw new NullPointerException("FormBody is null,please check it!");
        }
    }

    protected String getBodyParameter(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }
}
