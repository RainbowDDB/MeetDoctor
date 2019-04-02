package com.example.meetdoctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.example.meetdoctor.TestDelegate;
import com.example.meetdoctor.core.ProxyActivity;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.core.log.LatteLogger;

public class MainActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        final ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
//        LatteLogger.d("fuck","fuck");
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new TestDelegate();
    }
}
