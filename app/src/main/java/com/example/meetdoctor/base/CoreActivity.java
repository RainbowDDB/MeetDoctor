package com.example.meetdoctor.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.MessageConstant;
import com.example.meetdoctor.utils.EventBusUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * CoreActivity
 * 初始化友盟统计和EventBus
 * 以及封装一些常规操作
 */
public abstract class CoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayout() instanceof Integer) {
            setContentView((Integer) getLayout());
        } else if (getLayout() instanceof View) {
            setContentView((View) getLayout());
        } else {
            throw new RuntimeException("getLayout() is not a view or viewId, please check layout form");
        }
        initView();
    }

    protected abstract void initView();

    protected abstract Object getLayout();

    @Override
    protected void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBusUtils.unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    protected void startActivity(Class<?> clz, int flag) {
        Intent intent = new Intent(this, clz);
        intent.setFlags(flag);
        startActivity(intent);
    }

    protected void startActivity(Class<?> clz) {
        startActivity(clz, Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 接收到分发的事件
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage event) {
        if (event.getCode() == EventCode.NET_ERROR) {
            showToast(MessageConstant.NET_ERROR);
        }
    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventMessage event) {
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
