package com.example.meetdoctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meetdoctor.core.activity.ProxyActivity;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.utils.UIHelper;

import me.yokeyword.fragmentation.SupportHelper;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new WelcomeDelegate();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        UIHelper.setImmersiveStatusBar(getWindow());
        UIHelper.setAndroidNativeLightStatusBar(this, true);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 横向转场动画
        return new DefaultHorizontalAnimator();
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
                SupportHelper.hideSoftInput(v);
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
            View view = UIHelper.getViewAtActivity(
                    this, (int) event.getRawX(), (int) event.getRawY());
            // 除下述几种情况隔离
            if (view instanceof EditText || view instanceof Button) {
                return false;
            }
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right) || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }
}
