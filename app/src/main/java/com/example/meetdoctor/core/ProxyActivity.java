package com.example.meetdoctor.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.core.img.GlideApp;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.MessageConstant;
import com.example.meetdoctor.utils.EventBusUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.SupportActivity;

public abstract class ProxyActivity extends SupportActivity {

    public abstract LatteDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        // 全局父容器
        final FrameLayout container = new FrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBusUtils.register(this, this);
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
        EventBusUtils.unregister(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            GlideApp.get(this).clearMemory();
        }
        // 根据不同的level清理内存
        GlideApp.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // 内存低回调
        GlideApp.get(this).clearMemory();
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

    /**
     * 接收到分发的事件
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage event) {
        // 在此所有EventCode均为Restful常用code
        switch (event.getCode()) {
            case EventCode.NET_ERROR:
                showToast(MessageConstant.NET_ERROR);
                break;
            case EventCode.NOT_LOGIN:// 406
                showToast(MessageConstant.NOT_LOGINED);
                // 此时无需进行New Activity，故仅仅在全局检测登录状态的时候才会使用
// TODO               startActivity(LoginActivity.class);
                break;
            case EventCode.PARAMS_INVALID:// 401
                showToast(MessageConstant.PARAMS_UNAVAILABLE);
                break;
            case EventCode.UNEXPECTED_ERROR:// 404
                showToast(MessageConstant.DATABASE_ERROR);
                break;
            case EventCode.MEMBER_NOT_CREATED:// 402
                showToast(MessageConstant.MEMBER_NOT_CREATED);
                break;
            case EventCode.SERVER_ERROR:// 500
                showToast(MessageConstant.SERVER_ERROR);
                break;
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

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
