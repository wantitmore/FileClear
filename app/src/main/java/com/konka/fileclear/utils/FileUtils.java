package com.konka.fileclear.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by user001 on 2017-8-18.
 */

public class FileUtils {

    private static String[] clearType = { ".apk", ".log", ".tmp", ".temp", ".bak" };
    private static String SDCARD_ROOT = "/data/data";
    public static long deleteUselessFile() {
        Log.d(TAG, "deleteUselessFile: begin to delete.");
        return deleteFile(getallFiles(SDCARD_ROOT, clearType));
    }

    private static long deleteFile(List<File> files) {
        long allFileSize = 0;
        float size;
        for (File file : files) {
            size = getFileSize(file);
            Log.d(TAG, "deleteFile-->>  filePath:" + file.getPath()+ " | size:" + size);
            if(file.delete()){
                allFileSize = (long) (allFileSize + getFileSize(file));
                Log.e(TAG, "deleteFile: success, " + allFileSize);
            }
        }
        Log.d(TAG, "deleteFile: " + allFileSize);
        return allFileSize;
    }

    private static String numToString(float f) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String s = decimalFormat.format(f);
        return s;
    }

    private static float getFileSize(File file) {
        float size = 0;
        FileInputStream inputStream = null;
        try {
            Log.d(TAG, "getFilePath: " + file.getAbsolutePath());
            inputStream = new FileInputStream(file);
            size = inputStream.available();
            Log.e(TAG, "getFileSize: " + size );
        } catch (Exception e) {
            Log.d(TAG, "error is " + e.getMessage());
            // e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size / 1024;
    }

    private static List<File> fileList = new ArrayList<>();

    private static List<File> getallFiles(String sd, String[] clearType) {
        try {//遍历可能遇到.开头的文件
            File file = new File(sd);
            Log.d(TAG, "getallFiles: file is " + file + ", " + file.exists());
            if (file.exists()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File file2 : files) {
                        if (file2.isDirectory()) {
                            getallFiles(file2.getAbsolutePath(), clearType);// 递归查找
                        } else {
                            Log.d(TAG, "getallFiles: " + file2.getAbsolutePath());
                            for (String suffix: clearType) {
                                if (file2.getAbsolutePath().endsWith(suffix)) {
                                    Log.d(TAG, "getallFiles2: " + file2.getAbsolutePath());
                                    fileList.add(file2);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "getallFiles: error is " + e.getMessage());
            e.printStackTrace();
        }
        return fileList;
    }

}
