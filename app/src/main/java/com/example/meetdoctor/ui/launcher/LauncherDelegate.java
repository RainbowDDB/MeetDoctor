package com.example.meetdoctor.ui.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.meetdoctor.R;

import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.core.storage.LattePreference;
import com.example.meetdoctor.ui.user.LoginDelegate;

import java.util.ArrayList;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class LauncherDelegate extends LatteDelegate implements OnItemClickListener {

    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ConvenientBanner<Integer> mConvenientBanner = null;

    @Override
    public void onItemClick(int position) {
        // 如果点击的是最后一个，直接进入登录页
        if (position == INTEGERS.size() - 1) {
            startWithPop(new LoginDelegate());
        }
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
        INTEGERS.add(R.drawable.launcher_01);
        INTEGERS.add(R.drawable.launcher_02);
        INTEGERS.add(R.drawable.launcher_03);
        mConvenientBanner.setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }
}
