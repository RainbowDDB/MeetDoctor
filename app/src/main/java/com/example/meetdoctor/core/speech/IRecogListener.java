package com.example.meetdoctor.core.speech;

/**
 * 与SDK中回调参数的对应关系定义在RecogEventAdapter类中
 */
public interface IRecogListener {
    /**
     * CALLBACK_EVENT_ASR_READY
     * ASR_START 输入事件调用后，引擎准备完毕
     */
    void onReady();

    /**
     * CALLBACK_EVENT_ASR_BEGIN
     * onAsrReady后检查到用户开始说话
     */
    void onBegin();

    /**
     * CALLBACK_EVENT_ASR_END
     * 检查到用户开始说话停止，或者ASR_STOP 输入事件调用后，
     */
    void onEnd();

//    /**
//     * CALLBACK_EVENT_ASR_PARTIAL resultType=partial_result
//     * onAsrBegin 后 随着用户的说话，返回的临时结果
//     *
//     * @param recogResult 完整的结果
//     */
//    void onPartialResult(AsrPartialBean recogResult);
//
//    /**
//     * CALLBACK_EVENT_ASR_PARTIAL resultType=final_result
//     * 最终的识别结果
//     *
//     * @param results     可能返回多个结果，请取第一个结果
//     * @param recogResult 完整的结果
//     */
//    void onFinalResult(String[] results, AsrFinishBean recogResult);

    /**
     * CALLBACK_EVENT_ASR_PARTIAL resultType=final_result
     * or
     *
     * @param recogResult 完整正确的结果
     */
    void onFinishResult(String recogResult);

    /**
     * CALLBACK_EVENT_ASR_FINISH error=0
     */
    void onFinish();

    /**
     * CALLBACK_EVENT_ASR_FINISH error!=0
     *
     * @param errorCode    错误码
     * @param subErrorCode 错误字码
     * @param descMessage  错误信息
     */
    void onError(int errorCode, int subErrorCode, String descMessage);
}
