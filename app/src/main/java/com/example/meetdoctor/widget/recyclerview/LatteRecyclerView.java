package com.example.meetdoctor.widget.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.meetdoctor.core.img.GlideApp;

public class LatteRecyclerView extends RecyclerView {

    private Context mContext;
    /**
     * 滚动图片加载性能优化
     */
    OnScrollListener listener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    GlideApp.with(mContext).resumeRequests();// 加载
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    GlideApp.with(mContext).pauseRequests();// 暂停加载
                    break;
                case RecyclerView.SCROLL_STATE_IDLE:
                    GlideApp.with(mContext).resumeRequests();// 加载
                    break;
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    public LatteRecyclerView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        addOnScrollListener(listener);
    }

    public LatteRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        addOnScrollListener(listener);
    }

    public LatteRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        addOnScrollListener(listener);
    }
}
