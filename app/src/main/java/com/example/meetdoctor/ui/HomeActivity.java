package com.example.meetdoctor.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.speech.asr.SpeechConstant;
import com.bumptech.glide.Glide;
import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.core.net.callback.IError;
import com.example.meetdoctor.core.net.callback.ISuccess;
import com.example.meetdoctor.core.speech.RecogListener;
import com.example.meetdoctor.core.speech.SpeechRecognizer;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.event.CheckStateEvent;
import com.example.meetdoctor.ui.info.HealthProfileActivity;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.TimerHelper;
import com.example.meetdoctor.utils.UIHelper;

import java.util.LinkedHashMap;
import java.util.Map;

public class HomeActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    private static final String TAG = "HomeActivity";
    private LinearLayout inputBar;
    private EditText askContent;
    private long mPressedTime = 0;
    private DrawerLayout mDrawerLayout;
    private ImageView baymax;
    private Button speak;
    private ImageView soundOrText;
    private LinearLayout textInput;

    private SpeechRecognizer recognizer;

    @Override
    protected void onResume() {
        super.onResume();
        // 当键盘弹出隐藏的时候会 调用此方法。
        askContent.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            // 获取当前界面可视部分
            getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            // 获取屏幕的高度
            int screenHeight = getWindow().getDecorView().getRootView().getHeight();
            // 此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
            int heightDifference = screenHeight - r.bottom;
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) inputBar.getLayoutParams();
            if (lp.bottomMargin != heightDifference) {
                lp.setMargins(0, 0, 0, heightDifference);
                inputBar.setLayoutParams(lp);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        inputBar = findViewById(R.id.ask_input_bar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        LinearLayout mAILayout = headerView.findViewById(R.id.ai_layout);
        LinearLayout mCollectionLayout = headerView.findViewById(R.id.collection_layout);
        LinearLayout mProfileLayout = headerView.findViewById(R.id.health_profile_layout);
        LinearLayout mHistoryLayout = headerView.findViewById(R.id.history_layout);
        LinearLayout mSettingLayout = headerView.findViewById(R.id.setting_layout);

        askContent = findViewById(R.id.edt_ask_content);
        baymax = findViewById(R.id.img_baymax);
        speak = findViewById(R.id.btn_sound_input);
        soundOrText = findViewById(R.id.img_sound_or_text);
        textInput = findViewById(R.id.text_input);

        Glide.with(this).asGif().load(R.drawable.listen).into(baymax);

        recognizer = new SpeechRecognizer(this, new RecogListener() {
            @Override
            public void onFinishResult(String recogResult) {
                showToast(recogResult);
            }
        });

        mSettingLayout.setOnClickListener(this);
        mHistoryLayout.setOnClickListener(this);
        mProfileLayout.setOnClickListener(this);
        mCollectionLayout.setOnClickListener(this);
        mAILayout.setOnClickListener(this);
        soundOrText.setOnClickListener(this);
        speak.setOnTouchListener(this);

//        HttpUtils.getMemberList(this, new ISuccess() {
//            @Override
//            public void onSuccess(String response) {
//                LatteLogger.d(response);
//            }
//        }, new IError() {
//            @Override
//            public void onError(int code, String msg) {
//                LatteLogger.e(TAG, code + "   " + msg);
//            }
//        });
    }

    @Override
    public Object getLayout() {
        return R.layout.acticity_home;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ai_layout:
                break;
            case R.id.collection_layout:
                break;
            case R.id.history_layout:
                break;
            case R.id.health_profile_layout:
                startActivity(HealthProfileActivity.class);
                break;
            case R.id.setting_layout:
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
    public void onReceiveStickyEvent(EventMessage event) {
        super.onReceiveStickyEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            if (event.getData() instanceof CheckStateEvent) {
                LatteLogger.d("hhhhhhhhhhhhhh");
            }
        }
    }

    @Override
    public void onBackPressed() {
        // 关闭抽屉
        mDrawerLayout.closeDrawers();

        long mNowTime = System.currentTimeMillis();// 获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {// 比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {// 退出程序
            // 切换gif，并使inputBar消失
            Glide.with(this).asGif().load(R.drawable.sleep).into(baymax);
            inputBar.setVisibility(View.GONE);
            // 计时器延时gif持续时间 1s 并退出
            new TimerHelper() {
                @Override
                public void run() {
                    finish();
                    System.exit(0);
                }
            }.start(1000, 10000);
        }
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

    /**
     * 仅针对此页面gif格式图片做frameLayout背景
     * 而ScrollView失效的特殊情况
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            LatteLogger.d((int) event.getRawX() + "          " + (int) event.getRawY());
            if (UIHelper.getViewAtActivity(
                    this, (int) event.getRawX(), (int) event.getRawY()) instanceof EditText) {
                return false;
            }
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right) || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
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
}
