package com.example.meetdoctor.core.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.meetdoctor.core.activity.ProxyActivity;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.MessageConstant;
import com.example.meetdoctor.utils.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * BaseDelegate
 * 初始化友盟统计和EventBus
 * 以及封装一些常规操作
 */
public abstract class BaseDelegate extends SwipeBackFragment {

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        }
        if (rootView != null) {
            onBindView(savedInstanceState, rootView);
        }
        return attachToSwipeBack(rootView);
    }

    public final ProxyActivity getProxyActivity() {
        return (ProxyActivity) _mActivity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusUtils.register(getProxyActivity(), this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusUtils.unregister(getProxyActivity(), this);
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
//                start(new LoginDelegate());
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

    protected void showToast(String message) {
        Toast.makeText(getProxyActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
