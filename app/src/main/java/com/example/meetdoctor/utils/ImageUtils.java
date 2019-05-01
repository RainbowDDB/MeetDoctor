package com.example.meetdoctor.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.example.meetdoctor.core.img.GlideApp;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class ImageUtils {

    public static void showGif(Context context, int drawableId, ImageView imageView) {
        GlideApp.with(context)
                .asGif()
//                .skipMemoryCache(true)
                .priority(Priority.NORMAL)  // 低优先级提高性能
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
