package com.konka.fileclear.common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.konka.fileclear.entity.Audio;
import com.konka.fileclear.entity.Image;
import com.konka.fileclear.entity.Video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user001 on 2017-8-22.
 */

public class MediaResourceManager {

    private static final String TAG = "MediaResourceManager";

    public static List<Image> getImagesFromMedia(Context context) {
        ContentResolver mContentResolver = context.getContentResolver();
        ArrayList<Image> pictures = new ArrayList<>();
        Cursor c = null;
        try {
            c = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { "_id", "_data", "_size" }, null, null, null);
            while (c != null && c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                if (!new File(path).exists()) {
                    continue;
                }
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                Image image = new Image();
                image.setPath(path);
                pictures.add(image);
                Log.d(TAG, "getImagesFromMedia: audio " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return pictures;
    }

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

    public static List<Video> getVideosFromMedia(Context context) {
        ContentResolver mContentResolver = context.getContentResolver();
        List<Video> videos = new ArrayList<>();
        Cursor c = null;
        try {
            c = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            while (c != null && c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));// 路径
                if (!new File(path).exists()) {
                    continue;
                }
                int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));// 大小
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 歌曲名
                Video video = new Video(id, path, name);
                videos.add(video);
                Log.d(TAG, "getVideosFromMedia: name is " + name + ", " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return videos;
    }

    public static List<PackageInfo> getCustomApps(Context context) {
        List<PackageInfo> apps = new ArrayList<>();
        PackageManager pm = context.getPackageManager();

        List<PackageInfo> paklist = pm.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = paklist.get(i);
            if ((pak.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0 && !pak.packageName.equals("com.konka.fileclear")) {
                apps.add(pak);
                String str_name = pak.applicationInfo.loadLabel(pm).toString();
                Log.d(TAG, "getCustomApps: " + pak.packageName + ", " + str_name);
            }
        }
        return apps;
    }



}
