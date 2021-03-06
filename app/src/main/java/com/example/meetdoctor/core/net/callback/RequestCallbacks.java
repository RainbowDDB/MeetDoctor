package com.example.meetdoctor.core.net.callback;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.widget.loader.LatteLoader;
import com.example.meetdoctor.widget.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
/**
 * Latte-Core
 * 自定义回调，有IRequest,ISuccess,IFailure,IError多种回调可以任意使用
 * 且中间加入了加载Loading的实现
 * Created By Rainbow on 2019/4/30.
 */
public class RequestCallbacks implements Callback<String> {

    private static final String TAG = "RequestCallbacks";
    private static final Handler HANDLER = new Handler();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;

    public RequestCallbacks(IRequest request,
                            ISuccess success,
                            IFailure failure,
                            IError error,
                            LoaderStyle style) {
        REQUEST = request;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        LOADER_STYLE = style;
    }

    @Override
    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
        // 不管请求是否成功，先关闭Loader
        // 防止因 SUCCESS 或 ERROR 中因跳转关闭Activity出现的WindowLeaked情况
        stopLoading();
        if (response.isSuccessful()) {
            // call已执行
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
        stopLoading();
        LatteLogger.e(TAG, t.toString());
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
    }

    private void stopLoading() {
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(LatteLoader::stopLoading, 1000);
        }
    }
}
