package com.example.meetdoctor.core.speech;

import com.baidu.speech.EventListener;
import com.baidu.speech.asr.SpeechConstant;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.model.bean.AsrFinishBean;
import com.example.meetdoctor.model.bean.AsrPartialBean;
import com.google.gson.Gson;

/**
 * Latte-Core
 * Created By Rainbow on 2019/4/30.
 */
public class RecogEventAdapter implements EventListener {

    private IRecogListener listener;

    RecogEventAdapter(IRecogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
//        LatteLogger.d(name+"\n"+params);
        switch (name) {
            case SpeechConstant.CALLBACK_EVENT_ASR_READY:
                // 引擎就绪，可以说话，一般在收到此事件后通过UI通知用户可以说话了
                listener.onReady();
                break;
            case SpeechConstant.CALLBACK_EVENT_ASR_BEGIN:
                // 检测到用户的已经开始说话
                listener.onBegin();
                break;
            case SpeechConstant.CALLBACK_EVENT_ASR_END:
                // 检测到用户的已经停止说话
                listener.onEnd();
                break;
            case SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL:
                // 部分识别结果（还在录音中）
                AsrPartialBean partialBean = new Gson().fromJson(params, AsrPartialBean.class);
                String resultType = partialBean.getResult_type();
                // 只有识别到为最终结果才返回给onFinishResult(语义解析结果，划重点)
                if (resultType != null && resultType.equals("nlu_result")) {
                    listener.onFinishResult(new String(data, offset, length));
                } else if (resultType != null && resultType.equals("final_result")) {
                    // TODO do nothing?
                    LatteLogger.d(partialBean.getBest_result());
                } else {
                    // 精彩的布道！1603！
                    listener.onError(1, 1603, "未识别到结果");
                }
                break;
            case SpeechConstant.CALLBACK_EVENT_ASR_FINISH:
                // 识别结束
                AsrFinishBean finishBean = new Gson().fromJson(params, AsrFinishBean.class);
                String desc = finishBean.getDesc();
                if (desc != null && desc.equals("Speech Recognize success.")) {
                    listener.onFinish();
                } else {
                    int errorCode = finishBean.getError();
                    int errorSubCode = finishBean.getSub_error();
                    listener.onError(errorCode, errorSubCode, desc);
                }
                break;
            default:
                break;
        }
    }
}
