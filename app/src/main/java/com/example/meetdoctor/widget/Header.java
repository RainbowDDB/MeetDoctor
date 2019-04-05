package com.example.meetdoctor.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.utils.UIHelper;

@SuppressWarnings("unused")
public class Header extends AppBarLayout {

    public static final int DEFAULT_TITLE_COLOR = Color.parseColor("#3e3a39"); // R.color.textBlack
    public static final int DEFAULT_BAR_COLOR = Color.parseColor("#FFFFFF"); // R.color.pureWhite

    private Context mContext;

    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;
    private TextView mTitle;

    public Header(Context context) {
        super(context);

        this.mContext = context;
        init();
    }

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;
        init();
    }

    private void init() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.toolbar_header, this, true);

        mAppBarLayout = mView.findViewById(R.id.app_bar);
        mToolbar = mView.findViewById(R.id.toolbar);
        mTitle = mView.findViewById(R.id.toolbar_title);

        mAppBarLayout.setPadding(0, UIHelper.getStatusBarHeight(mContext), 0, 0);
        mToolbar.setTitle("");
        setTitleColor(DEFAULT_TITLE_COLOR);
        setBackgroundColor(DEFAULT_BAR_COLOR);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    public void setBackgroundColor(int color) {
        mAppBarLayout.setBackgroundColor(color);
        mToolbar.setBackgroundColor(color);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public AppBarLayout getAppBarLayout() {
        return mAppBarLayout;
    }
}
