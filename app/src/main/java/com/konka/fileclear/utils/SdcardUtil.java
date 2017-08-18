package com.konka.fileclear.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;

import static android.content.ContentValues.TAG;

/**
 * Created by user001 on 2017-8-9. SD card utils
 */

public class SdcardUtil {

    public static String getSDTotalSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        Log.d(TAG, "path is : " + path);
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    public static String getSDAvailableSize(Context context) {
        StatFs fs = new StatFs(Environment.getDataDirectory().getPath());
        return Formatter.formatFileSize(context, (fs.getAvailableBytes()));
    }

    private static long getByteTotal(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return blockSize * totalBlocks;
    }

    private static long getByteAvailable(Context context) {
        return new StatFs(Environment.getDataDirectory().getPath()).getAvailableBytes();
    }

    public static double getAvailRatio(Context context) {
        double num = 1- (double) getByteAvailable(context) / (double) getByteTotal(context);
        Log.d(TAG, "getAvailRatio: num is " + num);
        return num;
    }
}
