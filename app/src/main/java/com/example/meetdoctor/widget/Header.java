package com.example.meetdoctor.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.utils.UIHelper;

public class Header extends AppBarLayout {

    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#3e3a39"); // R.color.textBlack
    private static final int DEFAULT_BAR_COLOR = Color.parseColor("#FFFFFF"); // R.color.pureWhite
    private View mView;
    private Context mContext;

    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;
    private TextView mTitle;

    public Header(Context context) {
        super(context);

        this.mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.toolbar_header, this, true);
        init();
    }


    private void init() {
        mAppBarLayout = mView.findViewById(R.id.app_bar);
        mToolbar = mView.findViewById(R.id.toolbar);
        mTitle = mView.findViewById(R.id.toolbar_title);

        mAppBarLayout.setPadding(0, UIHelper.getStatusBarHeight(mContext), 0, 0);
        mToolbar.setTitle("");
        mToolbar.setBackgroundColor(DEFAULT_BAR_COLOR);

        ActionBar actionBar = ((AppCompatActivity) mContext).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setTitle(String title, int color) {
        mTitle.setText(title);
        mTitle.setTextColor(color);
    }

    public void setTitle(String title) {
        setTitle(title, DEFAULT_TITLE_COLOR);
    }

    public void setBackgroundColor(int color) {
        mToolbar.setBackgroundColor(color);
    }
}
