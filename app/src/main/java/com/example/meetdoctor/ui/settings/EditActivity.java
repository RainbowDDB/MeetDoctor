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
import com.example.meetdoctor.model.FlagConstant;
import com.example.meetdoctor.model.bean.MemberBean;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.StringUtils;
import com.example.meetdoctor.widget.DateSelector;

import java.util.ArrayList;

public class EditActivity extends SettingsBaseActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    @SuppressWarnings("unused")
    private static final String TAG = "EditActivity";
    private static final int TEXT_COLOR = Color.parseColor("#3e3a39");

    private EditText name;
    private int gender = 1; // 默认为 1，男
    private TextView birthday;
    private EditText alias;
    private RadioGroup radioGroup;

    private ArrayList<String> birthdayData = new ArrayList<>();
    // true为添加对象，false为编辑修改对象
    private boolean flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("编辑/添加对象");
    }

    @Override
    protected void initView() {
        name = findViewById(R.id.edt_person_name);
        birthday = findViewById(R.id.tv_person_birthday);
        alias = findViewById(R.id.edt_person_alias);

        radioGroup = findViewById(R.id.radio_group_gender);
        radioGroup.setOnCheckedChangeListener(this);

        birthday.setOnClickListener(this);
        Button confirm = findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(this);

        initData();
    }

    @Override
    protected Object getLayout() {
        return R.layout.activity_edit;
    }

    @Override
    public void onReceiveStickyEvent(EventMessage event) {
        super.onReceiveStickyEvent(event);
        if (event.getCode() == EventCode.SUCCESS) {
            if (event.getData() instanceof MemberBean) {
                MemberBean bean = (MemberBean) event.getData();
                name.setText(bean.getName());
                gender = bean.getGender();
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
            }
        }
//        else if (event.getCode() == EventCode.ADD_OR_EDIT) {
//            if (event.getData() instanceof Boolean) {
//                flag = (boolean) event.getData();
//            }
//        }
        EventBusUtils.removeStickyEvent(EventMessage.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (checkForm()) {
                    if (flag) {
                        // 添加对象
                        HttpUtils.createMember(this,
                                name.getText().toString(),
                                alias.getText().toString(),
                                gender,
                                null,
                                null,
                                birthday.getText().toString(),
                                response -> {
                                    showToast("创建对象成功！");
                                    finish();
                                });
                    } else {
                        // 编辑对象
                    }
                } else {
                    showToast("必须输入您的姓名和生日哦~");
                }
                break;
            case R.id.tv_person_birthday:
                DateSelector selectorView = new DateSelector(this, birthdayData);
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
                selectorView.showAtLocation(getParentView(), Gravity.BOTTOM, 0, 0);
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
        flag = getIntent().getBooleanExtra(FlagConstant.ADD_OR_EDIT, false);
    }
}
