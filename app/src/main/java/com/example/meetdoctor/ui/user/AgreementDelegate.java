package com.example.meetdoctor.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.widget.Header;

public class AgreementDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {
        return R.layout.activity_agreement;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Header header = rootView.findViewById(R.id.header);
        header.setTitle("遇医协议");
        setToolbar(header.getToolbar());
    }
}
