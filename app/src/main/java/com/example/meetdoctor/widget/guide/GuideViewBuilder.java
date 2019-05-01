package com.example.meetdoctor.widget.guide;

import android.content.Context;
import android.view.View;

import org.jetbrains.annotations.NotNull;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class GuideViewBuilder {

    private Context mContext;
    private View mView;

    private float mShadowAlpha = 0.7f;

    GuideViewBuilder(Context context) {
        this.mContext = context;
    }

    public final GuideViewBuilder setView(@NotNull View view) {
        this.mView = view;
        return this;
    }

    public final GuideViewBuilder setAlpha(float alpha) {
        this.mShadowAlpha = alpha;
        return this;
    }

    public final GuideView build() {
        return new GuideView(
                mContext,
                mView,
                mShadowAlpha);
    }
}
