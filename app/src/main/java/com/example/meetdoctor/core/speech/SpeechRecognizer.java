package com.example.meetdoctor.core.speech;

import android.content.Context;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.example.meetdoctor.core.log.LatteLogger;

import org.json.JSONObject;

import java.util.Map;

public class SpeechRecognizer {

    private static final String TAG = "SpeechRecognizer";
    // 是否加载离线资源
    private static boolean isOfflineEngineLoaded = false;
    // 未release前，只能new一个
    private static volatile boolean isInited = false;
    // SDK 内部核心 EventManager 类
    private EventManager asr;
    // SDK 内部核心 事件回调类，用于开发者写自己的识别回调逻辑
    private EventListener listener;

    public SpeechRecognizer(Context context, IRecogListener listener) {
        this(context, new RecogEventAdapter(listener));
    }

    private SpeechRecognizer(Context context, EventListener listener) {
        if (isInited) {
            LatteLogger.e(TAG, "还未调用release()，请勿新建一个新类");
            throw new RuntimeException("还未调用release()，请勿新建一个新类");
        }
        isInited = true;
        this.listener = listener;
        // 初始化asr的EventManager示例，多次得到的类，只能选一个使用
        asr = EventManagerFactory.create(context, "asr");
        // 设置回调event，识别引擎会回调这个类告知重要状态和识别结果
        asr.registerListener(listener);
    }

    /**
     * 开始识别
     *
     * @param params 识别参数
     */
    public void start(Map<String, Object> params) {
        // SDK集成步骤 拼接识别参数
        String json = new JSONObject(params).toString();
        LatteLogger.i(TAG, "开始录音-->录音参数为\n" + json);
        asr.send(SpeechConstant.ASR_START, json, null, 0, 0);
    }

    /**
     * 停止识别
     */
    public void stop() {
        if (!isInited) {
            throw new RuntimeException("release() was called");
        }
        LatteLogger.i(TAG, "结束录音");
        asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0);
    }

    /**
     * 取消识别，取消后将立即停止不会返回识别结果。
     * cancel 与 stop 的区别是 cancel在stop的基础上，完全停止整个识别流程，
     */
    public void cancel() {
        LatteLogger.i(TAG, "取消识别");
        if (!isInited) {
            throw new RuntimeException("release() was called");
        }
        // 取消本次识别
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
    }

    public void release() {
        if (asr == null) {
            return;
        }
        cancel();
        if (isOfflineEngineLoaded) {
            // 如果之前有调用过 加载离线命令词，这里要对应释放
            asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0);
            isOfflineEngineLoaded = false;
        }
        // 卸载listener
        asr.unregisterListener(listener);
        asr = null;
        isInited = false;
    }
}
