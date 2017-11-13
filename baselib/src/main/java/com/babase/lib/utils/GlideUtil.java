package com.babase.lib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;
import android.widget.ImageView;

import com.babase.lib.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;

import java.util.concurrent.ExecutionException;

/**
 * 图片加载类, 统一适配(方便换库,方便管理)
 * 需要什么方法, 就添加什么方法
 *
 * @author by bauer_bao on 16/8/2.
 */
public class GlideUtil {
    private static @DrawableRes
    int placeholderDrawableId, errorDrawableId;

    public static void setPlaceholderDrawableId(int placeholderDrawableId, int errorDrawableId) {
        GlideUtil.placeholderDrawableId = placeholderDrawableId;
        GlideUtil.errorDrawableId = errorDrawableId;
    }

    /**
     * 加载字节数组
     *
     * @param context
     * @param bytes
     * @param imageView
     */
    public static void load(Context context, byte[] bytes, ImageView imageView) {
        String url = bytes.toString();
        if (imageView.getTag(R.id.glide_image_tag) == null || !imageView.getTag(R.id.glide_image_tag).equals(url)) {
            Glide.with(context)
                    .load(bytes)
                    //565的图片，有些图片被过度压缩，导致图片泛绿，要么改成8888，要么修改缓存模式，缓存未压缩图片
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(placeholderDrawableId)
                    .error(errorDrawableId)
                    .dontAnimate()
                    .into(imageView);
            imageView.setTag(R.id.glide_image_tag, url);
        }
    }

    /**
     * 加载普通的图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void load(Context context, String url, ImageView imageView) {
        load(context, url, placeholderDrawableId, errorDrawableId, imageView);
    }

    /**
     * 加载url，定制占位图
     *
     * @param context
     * @param url
     * @param placehoderId
     * @param errorId
     * @param imageView
     */
    public static void load(Context context, String url, @DrawableRes int placehoderId, @DrawableRes int errorId, ImageView imageView) {
        if (imageView.getTag(R.id.glide_image_tag) == null || !imageView.getTag(R.id.glide_image_tag).equals(url)) {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(placehoderId)
                    .error(errorId)
                    .dontAnimate()
                    .into(imageView);
            imageView.setTag(R.id.glide_image_tag, url);
        }
    }

    /**
     * 加载url，可重新加载图片
     *
     * @param context
     * @param url
     * @param signature
     * @param imageView
     */
    public static void load(Context context, String url, String signature, ImageView imageView) {
        load(context, url, placeholderDrawableId, errorDrawableId, signature, imageView);
    }

    /**
     * 加载url，可重新加载图片，可定制占位图
     *
     * @param context
     * @param url
     * @param placehoderId
     * @param errorId
     * @param signature
     * @param imageView
     */
    public static void load(Context context, String url, @DrawableRes int placehoderId, @DrawableRes int errorId, String signature, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(placehoderId)
                .error(errorId)
                .signature(new StringSignature(signature))
                .dontAnimate()
                .into(imageView);
    }


    /**
     * 加载url，获取bitmap，设置监听
     *
     * @param context
     * @param url
     * @param simpleTarget
     */
    public static void load(Context context, String url, SimpleTarget simpleTarget) {
        load(context, url, placeholderDrawableId, errorDrawableId, simpleTarget);
    }

    /**
     * 加载url，获取bitmap，设置监听
     *
     * @param context
     * @param url
     * @param simpleTarget
     */
    public static void loadTarget(Context context, String url, int width, int height, SimpleTarget simpleTarget) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(placeholderDrawableId)
                .error(errorDrawableId)
                .dontAnimate()
                .override(width, height)
                .into(simpleTarget);
    }

    /**
     * 加载url，获取bitmap，定制占位图，设置监听
     *
     * @param context
     * @param url
     * @param placeholderId
     * @param errorId
     * @param simpleTarget
     */
    public static void load(Context context, String url, @DrawableRes int placeholderId, @DrawableRes int errorId, SimpleTarget simpleTarget) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(placeholderId)
                .error(errorId)
                .dontAnimate()
                .into(simpleTarget);
    }


    /**
     * 加载url，可获取对应尺寸的bitmap，可获取加载完成监听
     *
     * @param context
     * @param url
     * @param overrideWidth
     * @param overrideHeight
     * @param requestListener
     * @param imageView
     */
    public static void load(Context context, String url, int overrideWidth, int overrideHeight, boolean skipCache,
                            RequestListener<String, Bitmap> requestListener, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                //skipMemoryCache必须和diskCacheStrategy一起用，才可以跳过缓存
                .skipMemoryCache(skipCache)
                //默认使用RESULT
                .diskCacheStrategy(skipCache ? DiskCacheStrategy.NONE : DiskCacheStrategy.RESULT)
                .override(overrideWidth, overrideHeight)
                .listener(requestListener)
                .into(imageView);
    }

    /**
     * 加载url，获取bitmap，设置监听
     *
     * @param context
     * @param url
     * @param simpleTarget
     */
    public static void loadWithNoPlaceHolder(Context context, String url, SimpleTarget simpleTarget) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(simpleTarget);
    }

    /**
     * 加载url，根据宽高获取bitmap
     *
     * @param context
     * @param url
     * @param width
     * @param height
     * @return
     */
    public static Bitmap load(Context context, String url, int width, int height) {
        try {
            return Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(width, height)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载url，根据宽高获取bitmap
     *
     * @param context
     * @param url
     * @param width
     * @param height
     * @return
     */
    public static Bitmap load(Context context, String url, @DrawableRes int errorId, int width, int height) {
        try {
            return Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(errorId)
                    .into(width, height)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载url
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadGif(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 加载gif的url，但是也按照图片显示
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadGifAsImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 获取文件url
     *
     * @param path
     * @return
     */
    public static String getFileUrl(String path) {
        return "file://" + path;
    }

    /**
     * 获取asset路径
     *
     * @param assetFileName
     * @return
     */
    public static String getAssetUrl(String assetFileName) {
        return "file:///android_asset/" + assetFileName;
    }

    /**
     * 获取raw路径
     *
     * @param context
     * @param rawId
     * @return
     */
    public static String getRawUrl(Context context, @RawRes int rawId) {
        return "android.resource://" + context.getPackageName() + "/raw/" + rawId;
    }

    /**
     * 获取drawable路径
     *
     * @param context
     * @param drawbleId
     * @return
     */
    public static String getDrawableUrl(Context context, @DrawableRes int drawbleId) {
        return "android.resource://" + context.getPackageName() + "/drawable/" + drawbleId;
    }
}
