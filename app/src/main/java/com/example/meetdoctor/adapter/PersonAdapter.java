package com.example.meetdoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meetdoctor.R;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.bean.PersonBean;
import com.example.meetdoctor.ui.info.EditActivity;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.StringUtils;
import com.example.meetdoctor.widget.recyclerview.BaseRecyclerViewAdapter;
import com.example.meetdoctor.widget.recyclerview.RecyclerViewHolder;

import java.util.List;

public class PersonAdapter extends BaseRecyclerViewAdapter<PersonBean> {
    public PersonAdapter(Context context, List<PersonBean> data) {
        super(context, data, R.layout.item_person_card);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, PersonBean bean, int position) {
        TextView name = (TextView) holder.getView(R.id.tv_person_name);
        TextView age = (TextView) holder.getView(R.id.tv_person_age);
        TextView birthday = (TextView) holder.getView(R.id.tv_person_birthday);
        TextView alias = (TextView) holder.getView(R.id.tv_person_alias);
        ImageView genderImg = (ImageView) holder.getView(R.id.img_person_gender);
        ImageView edit = (ImageView) holder.getView(R.id.img_edit_person);

        name.setText(bean.getName());
        birthday.setText(bean.getBirthday());
        age.setText(String.valueOf(StringUtils.spilt2num(bean.getBirthday())[0]));
        alias.setText(bean.getAlias());

        if (bean.getGender() == 1) {
            Glide.with(getContext()).load(R.drawable.boy).into(genderImg);
        } else if (bean.getGender() == 0) {
            Glide.with(getContext()).load(R.drawable.girl).into(genderImg);
        } else {
            throw new RuntimeException("gender is not right,please check.");
        }
        edit.setOnClickListener(view -> {
            // 编辑个人档案信息事件
            EventBusUtils.postSticky(new EventMessage<>(EventCode.SUCCESS, bean));
            getContext().startActivity(new Intent(getContext(), EditActivity.class));
        });
    }
}
