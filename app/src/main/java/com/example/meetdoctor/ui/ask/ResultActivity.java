package com.example.meetdoctor.ui.ask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.base.BaseActivity;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.bean.AskResultBean;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.ImageUtils;
import com.example.meetdoctor.utils.UIHelper;
import com.example.meetdoctor.widget.Header;

public class ResultActivity extends BaseActivity {

    private static final String TAG = "ResultActivity";

    private TextView result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Header header = findViewById(R.id.header);
        setSupportActionBar(header.getToolbar());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initView() {
        ImageView baymax = findViewById(R.id.img_baymax);
        ImageUtils.showImg(this, R.drawable.baymax, baymax,
                UIHelper.dip2px(this, 150), UIHelper.dip2px(this, 191));

        result = findViewById(R.id.result);
    }

    @Override
    protected Object getLayout() {
        return R.layout.activity_result;
    }

    @Override
    public void onReceiveStickyEvent(EventMessage event) {
        super.onReceiveStickyEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            if (event.getData() instanceof AskResultBean.ResultContent) {
                AskResultBean.ResultContent resultContent = (AskResultBean.ResultContent) event.getData();
                result.setText(resultContent.toString());
//                showToast(resultContent.toString());
                EventBusUtils.removeStickyEvent(EventMessage.class);
            }
        }
    }
}
