package com.konka.fileclear.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by user001 on 2017-8-23.
 */

public class PictureLoader {
    private static LruCache<String, Bitmap> mMemoryCache;

    /* ImageLoader的实例 */
    private static PictureLoader mImageLoader;

    private PictureLoader() {
        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 5;
        // 设置图片缓存大小为程序最大可用内存的1/5
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();// 重写该方法可以在使用的时候获得
            }
        };
    }

    /**
     *
     * @return
     */
    public synchronized static PictureLoader getInstance() {
        if (mImageLoader == null) {
            synchronized (PictureLoader.class) {
                mImageLoader = new PictureLoader();
            }
        }
        return mImageLoader;
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param url
     *            LruCache的键，这里传入图片的URL地址。
     * @param bitmap
     *            LruCache的键，这里传入Bitmap对象。
     */
    public void addBitmapToMemoryCache(String url, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(url) == null) {
            synchronized (mMemoryCache) {
                if (url != null && bitmap != null) {
                    mMemoryCache.put(url, bitmap);
                }
            }
        }
    }

    /**
     * 获取视频图像
     *
     * @param videoPath
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath) {
        Bitmap bitmap;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.MINI_KIND);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 100, 100, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key
     *            LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(TextUtils.isEmpty(key) ? "" : key);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        // 源图片的宽度
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            // 计算出实际宽度和目标宽度的比率
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        Log.d("onBindViewHolder", "calculateInSampleSize: " + inSampleSize);
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth) {

        if (pathName.endsWith(".mp4") || pathName.endsWith(".avi") || pathName.endsWith(".3gp") || pathName.endsWith(".rmvb")) {
            return getVideoThumbnail(pathName);
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

}
