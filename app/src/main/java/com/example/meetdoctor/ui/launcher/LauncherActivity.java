package com.example.meetdoctor.ui.launcher;

import android.content.Intent;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.ui.LoginActivity;
import com.example.meetdoctor.core.storage.LattePreference;

import java.util.ArrayList;

public class LauncherActivity extends BaseActivity implements OnItemClickListener {

    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ConvenientBanner<Integer> mConvenientBanner = null;

    @Override
    public void initView() {
        INTEGERS.add(R.drawable.launcher_01);
        INTEGERS.add(R.drawable.launcher_02);
        INTEGERS.add(R.drawable.launcher_03);
        mConvenientBanner.setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public Object getLayout() {
        mConvenientBanner = new ConvenientBanner<>(this);
        return mConvenientBanner;
    }

    @Override
    public void onItemClick(int position) {
        // 如果点击的是最后一个，直接进入登录页
        if (position == INTEGERS.size() - 1) {
            LattePreference.setAppFlag(
                    ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
