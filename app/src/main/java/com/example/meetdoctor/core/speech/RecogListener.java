package com.example.meetdoctor.core.speech;

import com.example.meetdoctor.core.log.LatteLogger;

/**
 * Latte-Core
 * Created By Rainbow on 2019/4/30.
 */
public abstract class RecogListener implements IRecogListener {

    private static final String TAG = "RecogListener";

    @Override
    public void onReady() {
        LatteLogger.i(TAG, "引擎准备就绪，开始录音");
    }

    @Override
    public void onBegin() {
        LatteLogger.i(TAG, "用户开始说话");
    }

    @Override
    public void onEnd() {
        LatteLogger.i(TAG, "用户已停止说话");
    }

    @Override
    public void onFinish() {
        LatteLogger.i(TAG, "结束录音识别");
    }

    @Override
    public void onError(int errorCode, int subErrorCode, String descMessage) {
        LatteLogger.e(TAG, "解析错误,原因: " + descMessage +
                "\n错误码: " + errorCode + "\n错误子码：" + subErrorCode);
    }
}
