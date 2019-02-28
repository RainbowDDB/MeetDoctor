package com.example.meetdoctor.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.utils.UIHelper;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;

    @Override
    public void initView() {
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setPadding(0, UIHelper.getStatusBarHeight(this), 0, 0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        LinearLayout mAILayout = headerView.findViewById(R.id.ai_layout);
        LinearLayout mCollectionLayout = headerView.findViewById(R.id.collection_layout);
        LinearLayout mProfileLayout = headerView.findViewById(R.id.health_profile_layout);
        LinearLayout mHistoryLayout = headerView.findViewById(R.id.history_layout);
        LinearLayout mSettingLayout = headerView.findViewById(R.id.setting_layout);
        /*
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
                */
        mSettingLayout.setOnClickListener(this);
        mHistoryLayout.setOnClickListener(this);
        mProfileLayout.setOnClickListener(this);
        mCollectionLayout.setOnClickListener(this);
        mAILayout.setOnClickListener(this);
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
                startActivity(new Intent(this, HealthProfileActivity.class));
                break;
            case R.id.setting_layout:
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawers();
    }
}
