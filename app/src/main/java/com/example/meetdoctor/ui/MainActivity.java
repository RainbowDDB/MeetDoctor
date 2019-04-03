package com.example.meetdoctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.meetdoctor.core.app.ProxyActivity;
import com.example.meetdoctor.core.delegate.LatteDelegate;

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
        return new WelcomeDelegate();
    }
}
