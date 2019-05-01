package com.example.meetdoctor.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.utils.UIHelper;
import com.example.meetdoctor.widget.Header;

/**
 * Created By Rainbow on 2019/4/30.
 */
public abstract class SettingsBaseDelegate extends LatteDelegate {

    private Header header;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 状态栏字体颜色为白
        UIHelper.setAndroidNativeLightStatusBar(getProxyActivity(), false);

        header = rootView.findViewById(R.id.header);
        header.setTitleColor(getResources().getColor(R.color.pureWhite));
        header.setBackgroundColor(getResources().getColor(R.color.textBlack));

        getProxyActivity().setSupportActionBar(header.getToolbar());
        ActionBar actionBar = getProxyActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setTitle(String title) {
        header.setTitle(title);
    }
}
