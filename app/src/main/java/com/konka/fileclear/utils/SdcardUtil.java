package com.konka.fileclear.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;


/**
 * Created by user001 on 2017-8-9. SD card utils
 */

public class SdcardUtil {

    private static final String TAG = "SdcardUtil";

    public static String getSDTotalSize(final Context context) {
        long blockSize = 0;
        long totalBlocks = 0;
        try {
            File path = Environment.getExternalStorageDirectory();
            Log.d(TAG, "path is : " + path);
            StatFs stat = new StatFs(path.getPath());
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
        } catch (Exception e) {
            Log.e(TAG, "getSDTotalSize: error occur is " + e.getCause().getMessage());
            e.printStackTrace();
        }
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    public static long getSDAvailableSize(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            StatFs fs = new StatFs(Environment.getDataDirectory().getPath());
            return fs.getAvailableBytes();
        }
        return 0;
    }

    private static long getByteTotal(Context context) {
        long blockSize = 0;
        long totalBlocks = 0;
        try {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
        } catch (Exception e) {
            Log.e(TAG, "getByteTotal: error occur is " + e.getCause().getMessage());
            e.printStackTrace();
        }
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
