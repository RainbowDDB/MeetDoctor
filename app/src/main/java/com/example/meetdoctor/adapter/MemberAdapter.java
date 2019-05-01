package com.example.meetdoctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetdoctor.R;
import com.example.meetdoctor.core.log.LatteLogger;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.model.Constant;
import com.example.meetdoctor.model.bean.MemberBean;
import com.example.meetdoctor.ui.settings.EditDelegate;
import com.example.meetdoctor.utils.DateUtils;
import com.example.meetdoctor.utils.EventBusUtils;
import com.example.meetdoctor.utils.HttpUtils;
import com.example.meetdoctor.utils.ImageUtils;
import com.example.meetdoctor.utils.StringUtils;
import com.example.meetdoctor.widget.recycler.BaseRecyclerViewAdapter;
import com.example.meetdoctor.widget.recycler.RecyclerViewHolder;

import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class MemberAdapter extends BaseRecyclerViewAdapter<MemberBean> {

    private static final String TAG = "MemberAdapter";
    private int chosenId;
    private int chosenPosition;

    public MemberAdapter(Context context, List<MemberBean> data, int chosenId) {
        super(context, data, R.layout.item_person_card);
        this.chosenId = chosenId;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, MemberBean bean, int position) {
        View personCard = holder.getView(R.id.person_card_background);

        TextView name = (TextView) holder.getView(R.id.tv_person_name);
        TextView age = (TextView) holder.getView(R.id.tv_person_age);
        TextView birthday = (TextView) holder.getView(R.id.tv_person_birthday);
        TextView alias = (TextView) holder.getView(R.id.tv_person_alias);
        ImageView genderImg = (ImageView) holder.getView(R.id.img_person_gender);
        ImageView edit = (ImageView) holder.getView(R.id.img_edit_person);

        if (bean.getId() == chosenId) {
            chosenPosition = position;
            personCard.setBackgroundResource(R.drawable.item_person_card_selected);
        } else {
            personCard.setBackgroundResource(R.drawable.item_person_card_unselected);
        }

        name.setText(bean.getName() != null ? bean.getName() : "未填写");
        age.setText(bean.getBirthday() != null ?
                String.valueOf(DateUtils.getYear() - StringUtils.spilt2num(bean.getBirthday())[0]) + " 岁" : "未填写");
        birthday.setText(bean.getBirthday() != null ? bean.getBirthday() : "未填写");
        alias.setText(bean.getAlias() != null ? bean.getAlias() : "未填写");

        if (bean.getGender() == 1) {
            ImageUtils.showImg(getContext(), R.drawable.boy, genderImg);
        } else if (bean.getGender() == 0) {
            ImageUtils.showImg(getContext(), R.drawable.girl, genderImg);
        } else {
            throw new RuntimeException("gender is not right,please check.");
        }
        edit.setOnClickListener(view -> {
            // 编辑个人档案信息事件
            EventBusUtils.postSticky((Activity) getContext(),
                    new EventMessage<>(EventCode.SUCCESS, bean));
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constant.ADD_OR_EDIT, false);
            EditDelegate editDelegate = new EditDelegate();
            editDelegate.setArguments(bundle);
            ((SupportActivity) getContext()).start(editDelegate);
        });

        holder.itemView.setOnClickListener(view ->
                HttpUtils.switchMember(getContext(), bean.getId(), response -> {
                    LatteLogger.i(TAG, response);
                    chosenId = bean.getId();
                    notifyItemChanged(chosenPosition);
                    notifyItemChanged(position);
                    chosenPosition = position;
                }));
    }
}
