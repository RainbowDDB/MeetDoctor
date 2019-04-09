package com.example.meetdoctor.ui.ask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.delegate.LatteDelegate;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.bean.AskResultBean;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.ImageUtils;
import com.example.meetdoctor.utils.UIHelper;
import com.example.meetdoctor.widget.Header;

public class ResultDelegate extends LatteDelegate {

    private static final String TAG = "ResultActivity";

    private TextView result;

    @Override
    public Object setLayout() {
        return R.layout.delegate_result;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Header header = rootView.findViewById(R.id.header);
        setToolbar(header.getToolbar());

        ImageView baymax = rootView.findViewById(R.id.img_baymax);
        ImageUtils.showImg(getProxyActivity(), R.drawable.baymax, baymax,
                UIHelper.dip2px(getProxyActivity(), 150),
                UIHelper.dip2px(getProxyActivity(), 191));

        result = rootView.findViewById(R.id.result);
    }

    @Override
    public void onReceiveStickyEvent(EventMessage event) {
        super.onReceiveStickyEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            if (event.getData() instanceof AskResultBean.ResultContent) {
                AskResultBean.ResultContent resultContent = (AskResultBean.ResultContent) event.getData();
                result.setText(resultContent.toString());
//                showToast(resultContent.toString());
                EventBusUtils.removeStickyEvent(getProxyActivity(), EventMessage.class);
            }
        }
    }
}
