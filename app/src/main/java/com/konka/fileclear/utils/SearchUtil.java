package com.konka.fileclear.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.util.Log;

import com.konka.fileclear.entity.BigFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;

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
        Log.d(TAG, "getSupportFileList: --" + uri.toString());
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

    private ArrayList<BigFile> bigFileList = new ArrayList<>();
    public ArrayList<BigFile> getBigFileList(Context context, String path) {

        File file = new File(path);
//        Log.d(TAG, "getallFiles: file is " + file + ", " + file.exists());
        if (bigFileList != null) {
            Log.d(TAG, "getBigFileList: ---------zack  " + bigFileList.size());
        }
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File file2 : files) {
                    if (file2 == null) {
                        continue;
                    }
                    if (file2.isDirectory()) {
                        getBigFileList(context, file2.getAbsolutePath());// 递归查找
                    } else {
                        long fileSize;
                        try {
                            File copyf = new File(file2.getAbsolutePath());
                            FileInputStream fis;
                            fis = new FileInputStream(copyf);
                            fileSize = (long) fis.available();
                            fis.close();
                            if (fileSize > 10240 * 1024) {  // larger than 10M
                                BigFile bigFile = new BigFile();
                                String bigFilePath = file2.getAbsolutePath();
                                int lastIndex = bigFilePath.lastIndexOf("/");
                                String name = bigFilePath.substring(lastIndex + 1);
                                String size = Formatter.formatFileSize(context, fileSize);
                                bigFile.setName(name);
                                bigFile.setPath(bigFilePath);
                                bigFile.setSize(size);
                                bigFile.setRealSize(fileSize);
                                bigFileList.add(bigFile);
                                Collections.sort(bigFileList);
                                Log.d(TAG, "getallFiles: " + file2.getAbsolutePath() + ", size is " + size);
                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG, "getBigFileList: error " + e.getCause().getMessage());
                        }
                    }
                }
            }
        }
        Log.d(TAG, "getBigFileList: big file size is " + bigFileList.size());
        return bigFileList;
    }
}
