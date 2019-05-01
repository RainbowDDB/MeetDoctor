package com.example.meetdoctor.ui.launcher;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.meetdoctor.utils.ImageUtils;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class LauncherHolder implements Holder<Integer> {

    private ImageView mImageView = null;

    @Override
    public View createView(Context context) {
        mImageView = new ImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        ImageUtils.showImg(context, data, mImageView);
    }
}
