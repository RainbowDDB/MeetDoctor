package com.example.meetdoctor.ui;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speech.asr.SpeechConstant;
import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.core.speech.RecogListener;
import com.example.meetdoctor.core.speech.SpeechRecognizer;
import com.example.meetdoctor.model.Constant;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.MessageConstant;
import com.example.meetdoctor.model.bean.AskResultBean;
import com.example.meetdoctor.model.event.CheckStateEvent;
import com.example.meetdoctor.ui.settings.SettingsDelegate;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.ImageUtils;
import com.example.meetdoctor.utils.TimerHelper;
import com.example.meetdoctor.utils.UIHelper;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

public class HomeDelegate extends LatteDelegate implements
        View.OnClickListener, View.OnTouchListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    @SuppressWarnings("unused")
    private static final String TAG = "HomeActivity";
    private LinearLayout inputBar;
    private EditText askContent;
    private long mPressedTime = 0;
    private DrawerLayout mDrawerLayout;
    private ImageView baymax;
    private Button speak;
    private ImageView soundOrText;
    private LinearLayout textInput;
    private TextView response;
    private ImageView background;

    private SpeechRecognizer recognizer;

    @Override
    public Object setLayout() {
        return R.layout.acticity_home;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        inputBar = rootView.findViewById(R.id.ask_input_bar);

        mDrawerLayout = rootView.findViewById(R.id.drawer_layout);
        NavigationView navView = rootView.findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        LinearLayout mAILayout = headerView.findViewById(R.id.ai_layout);
        LinearLayout mCollectionLayout = headerView.findViewById(R.id.collection_layout);
        LinearLayout mProfileLayout = headerView.findViewById(R.id.health_profile_layout);
        LinearLayout mHistoryLayout = headerView.findViewById(R.id.history_layout);
        LinearLayout mSettingLayout = headerView.findViewById(R.id.settings_layout);
        background = rootView.findViewById(R.id.img_home_background);

        askContent = rootView.findViewById(R.id.edt_ask_content);
        baymax = rootView.findViewById(R.id.img_baymax);
        speak = rootView.findViewById(R.id.btn_sound_input);
        soundOrText = rootView.findViewById(R.id.img_sound_or_text);
        textInput = rootView.findViewById(R.id.text_input);
        response = rootView.findViewById(R.id.tv_ask_result);
        Button ask = rootView.findViewById(R.id.btn_ask);

        recognizer = new SpeechRecognizer(getContext(), new RecogListener() {
            @Override
            public void onFinishResult(String recogResult) {
                showToast(recogResult);
                response.setText(recogResult);
            }
        });

        mSettingLayout.setOnClickListener(this);
        mHistoryLayout.setOnClickListener(this);
        mProfileLayout.setOnClickListener(this);
        mCollectionLayout.setOnClickListener(this);
        mAILayout.setOnClickListener(this);
        soundOrText.setOnClickListener(this);
        speak.setOnTouchListener(this);
        ask.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        ImageUtils.showImg(getContext(), R.drawable.home_background, background);
        ImageUtils.showGif(getContext(), R.drawable.listen, baymax);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当键盘弹出隐藏的时候会 调用此方法。
        askContent.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            askContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            askContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        // 获取当前界面可视部分
        View decorView = getProxyActivity().getWindow().getDecorView();
        decorView.getWindowVisibleDisplayFrame(r);
        // 获取屏幕的高度
        int screenHeight = decorView.getRootView().getHeight();
        // 此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
        int heightDifference = screenHeight - r.bottom;
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) inputBar.getLayoutParams();
        if (lp.bottomMargin != heightDifference) {
            lp.setMargins(0, 0, 0,
                    heightDifference + UIHelper.getVirtualBarHeight(getContext()));
            inputBar.setLayoutParams(lp);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ask:
                String content = askContent.getText().toString();
                response.setText(content);
                ask(content);
                askContent.setText(null);
                break;
            case R.id.ai_layout:
                break;
            case R.id.collection_layout:
// TODO               startActivity(CollectionActivity.class);
                break;
            case R.id.history_layout:
// TODO               startActivity(HistoryActivity.class);
                break;
            case R.id.health_profile_layout:
// TODO               startActivity(HealthProfileActivity.class);
                break;
            case R.id.settings_layout:
                start(new SettingsDelegate());
                break;
            case R.id.img_sound_or_text:
                if (speak.getVisibility() == View.VISIBLE) {
                    speak.setVisibility(View.GONE);
                    textInput.setVisibility(View.VISIBLE);
                    soundOrText.setImageDrawable(getResources().getDrawable(R.drawable.sound));
                } else {
                    speak.setVisibility(View.VISIBLE);
                    textInput.setVisibility(View.GONE);
                    soundOrText.setImageDrawable(getResources().getDrawable(R.drawable.keyboard));
                }
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        super.onReceiveEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            if (event.getData() instanceof String) {
                AskResultBean resultBean = new Gson().fromJson(
                        String.valueOf(event.getData()), AskResultBean.class);
                if (resultBean.getCode() == 1) {
                    // success
                    switch (resultBean.getType()) {
                        case Constant.AskType.RE_ASK: // type=4 重开问询，引导进入结果页
                            EventBusUtils.postSticky(getProxyActivity(), new EventMessage<>(
                                    EventCode.SUCCESS,
                                    resultBean.getResultContent()
                            ));
//                            startActivity(ResultActivity.class);
                            break;
                        default: // type= 1 2 3
                            break;
                    }
                } else {
                    showToast(MessageConstant.SERVER_ERROR);
                }
            }
        }
    }

    @Override
    public void onReceiveStickyEvent(EventMessage event) {
        super.onReceiveStickyEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            if (event.getData() instanceof CheckStateEvent) {
                // TODO 处理上次对话记录
                CheckStateEvent bean = (CheckStateEvent) event.getData();
                if (bean.getState() == 1) {
                    // TODO 问用户是否继续之前问询
                    String[] list = bean.getList();
                }
                EventBusUtils.removeStickyEvent(getProxyActivity(), EventMessage.class);
            }
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        // 关闭抽屉
        mDrawerLayout.closeDrawers();

        long mNowTime = System.currentTimeMillis();// 获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {// 比较两次按键时间差
            Toast.makeText(getContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {// 退出程序
            // 切换gif，并使inputBar消失
            // TODO 过度绘制现象，需优化
            ImageUtils.showGif(getContext(), R.drawable.sleep, baymax);
            inputBar.setVisibility(View.GONE);
            // 计时器延时gif持续时间 1s 并退出
            new TimerHelper() {
                @Override
                public void run() {
                    getProxyActivity().finish();
                    System.exit(0);
                }
            }.start(1000);
        }
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start();
                break;
            case MotionEvent.ACTION_UP:
                recognizer.stop();
                break;
            default:
                break;
        }
        return false;
    }

    private void start() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.VAD, SpeechConstant.VAD_TOUCH);
        // params.put(SpeechConstant.NLU, "enable");
        // params.put(SpeechConstant.VAD_END POINT_TIMEOUT, 0); // 长语音
        // params.put(SpeechConstant.IN_FILE, "res:///com/baidu/android/voicedemo/16k_test.pcm");
        // params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
        params.put(SpeechConstant.PID, 15373); // 中文输入法模型，有逗号，语义解析
        recognizer.start(params);
    }

    private void ask(String content) {
        HttpUtils.ask(getProxyActivity(),
                Constant.AskType.NORMAL_ASK,
                content,
                response -> EventBusUtils.post(getProxyActivity(), new EventMessage<>(
                        EventCode.SUCCESS,
                        response
                )));
    }
}
