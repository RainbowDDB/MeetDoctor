package com.example.meetdoctor.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
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
}