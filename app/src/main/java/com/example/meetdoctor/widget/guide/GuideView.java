package com.example.meetdoctor.widget.guide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.meetdoctor.core.storage.LattePreference;
import com.example.meetdoctor.utils.UIHelper;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class GuideView extends RelativeLayout {

    /**
     * targetView前缀。SHOW_GUIDE_PREFIX + targetView.getId()作为保存在SP文件的key。
     */
    private static final String SHOW_GUIDE_PREFIX = "show_guide_on_view_";

    private Context mContext;
    private View mView;
    private float mShadowAlpha;

    private Canvas mCanvas;
    private Paint mShadowPaint;

    private boolean hasShown = false; // 是否已经进入过引导View
    private boolean isFirst = true; // 防止onDraw多次绘制

    public GuideView(Context context, View view, float shadowAlpha) {
        this(context);
        this.mView = view;
        this.mShadowAlpha = shadowAlpha;

        init();
    }

    public GuideView(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            isFirst = false;
        }
    }

    public final GuideViewBuilder builder() {
        return new GuideViewBuilder(mContext);
    }

    private void init() {
        hasShown = hasShown();
        // 解决onDraw方法不执行的问题
        setWillNotDraw(false);

        mShadowPaint = new Paint();
        mShadowPaint.setAntiAlias(true); // 抗锯齿
        UIHelper.setBackgroundAlpha(((Activity) mContext).getWindow(), mShadowAlpha);
    }

    public final void show() {
        if (!hasShown) {
            // 首次进入才进行显示
            setOnceFlag();
        }
    }

    public final void hide() {

    }

    // 是否已经显示过
    private boolean hasShown() {
        return LattePreference.getAppFlag(generateUniqueId(mView));
    }

    // 配置缓存，此页面已经显示
    private void setOnceFlag() {
        if (mView != null) {
            LattePreference.setAppFlag(generateUniqueId(mView), true);
        }
    }

    // 获取此页面独一id
    private String generateUniqueId(View view) {
        return SHOW_GUIDE_PREFIX + view.getId();
    }
}
