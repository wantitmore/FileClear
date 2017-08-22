package com.konka.fileclear.common;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.konka.fileclear.entity.Audio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user001 on 2017-8-22.
 */

public class MediaResourceManager {

    private static final String TAG = "MediaResourceManager";

//    public static List<String> getImagesFromMedia() {
//        ArrayList<String> pictures = new ArrayList<String>();
//        Cursor c = null;
//        try {
//            c = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { "_id", "_data", "_size" }, null, null, null);
//            while (c.moveToNext()) {
//                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
//                /*if (!FileUtils.isExists(path)) {
//                    continue;
//                }
//                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
//                FileCategoryPageFragment.mAllPictureSize += size;
//                pictures.add(path);*/
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (c != null) {
//                c.close();
//            }
//        }
//        return pictures;
//    }

    public static List<Audio> getAudiosFromMedia(Context context) {
        ContentResolver mContentResolver = context.getContentResolver();
        List<Audio> audios = new ArrayList<>();

        Cursor c = null;
        try {
            c = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            while (c != null && c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));// 路径
                if (!new File(path).exists()) {
                    continue;
                }
                int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));// 大小
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)); // 歌曲名
                Audio audio = new Audio(id, name, path);
                audios.add(audio);
                Log.d(TAG, "getAudiosFromMedia: audio " + name);
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "getAudiosFromMedia: Nullerror " + e.getCause().getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(TAG, "getAudiosFromMedia: error " + e.getCause().getMessage());
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return audios;
    }
}
