package com.example.meetdoctor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.meetdoctor.core.img.GlideApp;

public class ImageUtils {

    public static void showGif(Context context, int drawableId, ImageView imageView) {
        GlideApp.with(context)
                .asGif()
//                .skipMemoryCache(true)
                .priority(Priority.LOW)  // 低优先级提高性能
                .load(drawableId)
                .into(imageView);
    }

    public static void showImg(Context context, int drawableId, ImageView imageView) {
        GlideApp.with(context)
                .asDrawable()
                .load(drawableId)
                .dontAnimate()   // 禁止动画提高RecyclerView的性能
                .into(imageView);
    }

    public static void showImg(Context context, int drawableId, ImageView imageView, int height, int width) {
        GlideApp.with(context)
                .asDrawable()
                .load(drawableId)
                .override(width, height)
                .dontAnimate()   // 禁止动画提高RecyclerView的性能
                .into(imageView);
    }
}
