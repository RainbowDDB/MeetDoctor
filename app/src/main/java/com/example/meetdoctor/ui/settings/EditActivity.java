package com.example.meetdoctor.ui.settings;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.core.net.callback.ISuccess;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.bean.PersonBean;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.StringUtils;
import com.example.meetdoctor.widget.DateSelector;

import java.util.ArrayList;

public class EditActivity extends SettingsBaseActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    @SuppressWarnings("unused")
    private static final String TAG = "EditActivity";
    private EditText name;
    private int gender;
    private TextView birthday;
    private EditText alias;

    private RadioGroup radioGroup;

    private ArrayList<String> birthdayData = new ArrayList<>();

    @Override
    protected void initView() {
        name = findViewById(R.id.edt_person_name);
        birthday = findViewById(R.id.tv_person_birthday);
        alias = findViewById(R.id.edt_person_alias);

        radioGroup = findViewById(R.id.radio_group_gender);
        radioGroup.setOnCheckedChangeListener(this);

        birthday.setOnClickListener(this);
        Button confirm = findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(view -> {

        });

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
            if (event.getData() instanceof PersonBean) {
                PersonBean bean = (PersonBean) event.getData();
                name.setText(bean.getName());
                gender = bean.getGender();
                radioGroup.check(gender == 1 ? R.id.radio_boy : R.id.radio_girl);
                if (bean.getBirthday() != null && !bean.getBirthday().equals("")) {
                    int[] dates = StringUtils.spilt2num(bean.getBirthday());
                    birthday.setText(StringUtils.getFormatDate(dates[0], dates[1], dates[2]));
                    birthday.setTextColor(getResources().getColor(R.color.textBlack));
                    for (int i = 0; i < dates.length; i++) {
                        birthdayData.add(i, String.valueOf(dates[i]));
                    }
                }
                if (bean.getAlias() != null && !bean.getAlias().equals("")) {
                    alias.setText(bean.getAlias());
                    alias.setTextColor(getResources().getColor(R.color.textBlack));
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
//                HttpUtils.createMember(this,
//                        name.getText().toString(),
//                        alias.getText().toString(), 1,
//                        null, null, "1999-03-04"
//                        , response -> {
//                            LatteLogger.d(response);
//                        });
                break;
            case R.id.tv_person_birthday:
                DateSelector selectorView = new DateSelector(this, birthdayData);
                selectorView.setOnConfirmClickListener((year, month, day) -> {
                    birthdayData.set(0, year);
                    birthdayData.set(1, month);
                    birthdayData.set(2, day);
                    birthday.setText(StringUtils.getFormatDate(
                            Integer.parseInt(year),
                            Integer.parseInt(month),
                            Integer.parseInt(day)));
                });
                selectorView.showAtLocation(getParentView(), Gravity.BOTTOM, 0, 0);
                break;
        }
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

    }
}