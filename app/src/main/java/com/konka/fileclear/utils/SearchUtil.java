package com.konka.fileclear.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by user001 on 2017-8-15.
 */

public class SearchUtil {

    private static final String TAG = "SearchUtil";

    public static ArrayList<String> getSupportFileList(Context context, String[] searchFileSuffix) {
        ArrayList<String> searchFileList;
        if (null == context || null == searchFileSuffix || searchFileSuffix.length == 0) {
            return null;
        }

        String searchPath = "";
        int length = searchFileSuffix.length;
        for (int index = 0; index < length; index++) {
            searchPath += (MediaStore.Files.FileColumns.DATA + " LIKE '%" + searchFileSuffix[index] + "' ");
            if ((index + 1) < length) {
                searchPath += "or ";
            }
        }

        searchFileList = new ArrayList<>();
        Uri uri = MediaStore.Files.getContentUri("external");
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Files.FileColumns.DATA}, searchPath, null, null);
            Log.d(TAG, "getSupportFileList: search is" + searchPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String filepath;
        if (cursor == null) {
            Log.d(TAG, "getSupportFileList: cursor is null~");
        } else {
            if (cursor.moveToFirst()) {
                do {
                    filepath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    try {
                        searchFileList.add(filepath);
                        Log.d(TAG, "getSupportFileList filepath:" + filepath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return searchFileList;
    }

    public static ArrayList<String> getBigFileList(Context context, String path) {

        ArrayList<String> searchFileList = new ArrayList<>();
        File file = new File(path);
//        Log.d(TAG, "getallFiles: file is " + file + ", " + file.exists());
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File file2 : files) {
                    if (file2 == null) {
                        continue;
                    }
                    if (file2.isDirectory()) {
//                        Log.d(TAG, "getallFiles: file is " + file);
                        getBigFileList(context, file2.getAbsolutePath());// 递归查找
                    } else {
                        long fileSize;
                        try {
                            File copyf = new File(file2.getAbsolutePath());
                            FileInputStream fis;
                            fis = new FileInputStream(copyf);
                            fileSize = (long) fis.available();

                            if (fileSize > 10240 * 1024) {  // larger than 10M
                                searchFileList.add(file2.getAbsolutePath());
                                String fileSize1 = Formatter.formatFileSize(context, fileSize);
                                Log.d(TAG, "getallFiles: " + file2.getAbsolutePath() + ", size is " + fileSize1);
                            }
                        } catch (Exception e) {
                            Log.d(TAG, "getBigFileList: error " + e.getCause().getMessage());
                        }
                    }
                }
            }
        }
        return searchFileList;
    }
}
