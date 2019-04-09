package com.example.meetdoctor.ui.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.Constant;
import com.example.meetdoctor.model.bean.MemberBean;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.StringUtils;
import com.example.meetdoctor.widget.DateSelector;

import java.util.ArrayList;

public class EditDelegate extends SettingsBaseDelegate implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    @SuppressWarnings("unused")
    private static final String TAG = "EditActivity";
    private static final int TEXT_COLOR = Color.parseColor("#3e3a39");

    private EditText name;
    private int gender = 1; // 默认为 1，男
    private int memberId;
    private TextView birthday;
    private EditText height;
    private EditText weight;
    private EditText alias;
    private RadioGroup radioGroup;

    private ArrayList<String> birthdayData = new ArrayList<>();
    // true为添加对象，false为编辑修改对象
    private boolean flag;

    @Override
    public Object setLayout() {
        return R.layout.delegate_edit;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        setTitle("编辑/添加对象");

        name = rootView.findViewById(R.id.edt_person_name);
        birthday = rootView.findViewById(R.id.tv_person_birthday);
        height = rootView.findViewById(R.id.edt_person_height);
        weight = rootView.findViewById(R.id.edt_person_weight);
        alias = rootView.findViewById(R.id.edt_person_alias);

        radioGroup = rootView.findViewById(R.id.radio_group_gender);
        radioGroup.setOnCheckedChangeListener(this);

        birthday.setOnClickListener(this);
        Button confirm = rootView.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(this);

        initData();
    }

    @Override
    public void onReceiveStickyEvent(EventMessage event) {
        super.onReceiveStickyEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            if (event.getData() instanceof MemberBean) {
                MemberBean bean = (MemberBean) event.getData();
                name.setText(bean.getName());
                gender = bean.getGender();
                memberId = bean.getId();
                radioGroup.check(gender == 1 ? R.id.radio_boy : R.id.radio_girl);
                if (bean.getBirthday() != null && !bean.getBirthday().equals("")) {
                    int[] dates = StringUtils.spilt2num(bean.getBirthday());
                    birthday.setText(StringUtils.getFormatDate(dates[0], dates[1], dates[2]));
                    birthday.setTextColor(TEXT_COLOR);
                    for (int i = 0; i < dates.length; i++) {
                        birthdayData.add(i, String.valueOf(dates[i]));
                    }
                }
                if (bean.getAlias() != null && !bean.getAlias().equals("")) {
                    alias.setText(bean.getAlias());
                }
                if (bean.getHeight() != null) {
                    height.setText(String.valueOf(bean.getHeight()));
                }
                if (bean.getWeight() != null) {
                    weight.setText(String.valueOf(bean.getWeight()));
                }
            }
        }
        EventBusUtils.removeStickyEvent(getProxyActivity(), EventMessage.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (checkForm()) {
                    String h = height.getText().toString();
                    String w = weight.getText().toString();
                    if (flag) {
                        // 添加对象
                        HttpUtils.createMember(getProxyActivity(),
                                name.getText().toString(),
                                alias.getText().toString(),
                                gender,
                                h.equals("") ? Double.valueOf(h) : null,
                                w.equals("") ? Double.valueOf(w) : null,
                                birthday.getText().toString(),
                                response -> {
                                    showToast("创建对象成功！");
                                    pop();
                                });
                    } else {
                        // 编辑对象
                        HttpUtils.modifyMember(getProxyActivity(),
                                memberId,
                                name.getText().toString(),
                                alias.getText().toString(),
                                gender,
                                h.equals("") ? Double.valueOf(h) : null,
                                w.equals("") ? Double.valueOf(w) : null,
                                birthday.getText().toString(),
                                response -> {
                                    showToast("修改对象成功！");
                                    pop();
                                });
                    }
                } else {
                    showToast("必须输入您的姓名和生日哦~");
                }
                break;
            case R.id.tv_person_birthday:
                DateSelector selectorView = new DateSelector(getProxyActivity(), birthdayData);
                selectorView.setOnConfirmClickListener((year, month, day) -> {
                    if (birthdayData.size() == 0) {
                        birthdayData.add(year);
                        birthdayData.add(month);
                        birthdayData.add(day);
                    } else {
                        birthdayData.set(0, year);
                        birthdayData.set(1, month);
                        birthdayData.set(2, day);
                    }
                    birthday.setText(StringUtils.getFormatDate(
                            Integer.parseInt(year),
                            Integer.parseInt(month),
                            Integer.parseInt(day)));
                    birthday.setTextColor(TEXT_COLOR);
                });
                selectorView.showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    private boolean checkForm() {
        return !name.getText().toString().equals("")
                && !birthday.getText().toString().equals("");
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_boy:
                gender = 1;
                break;
            case R.id.radio_girl:
                gender = 0;
                break;
        }
    }

    private void initData() {
        if (getArguments() != null) {
            flag = getArguments().getBoolean(Constant.ADD_OR_EDIT);
        } else {
            flag = false;
        }
    }
}
