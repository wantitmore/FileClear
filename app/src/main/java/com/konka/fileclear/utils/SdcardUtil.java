package com.konka.fileclear.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

/**
 * Created by user001 on 2017-8-9. SD card utils
 */

public class SdcardUtil {

    public static String getSDTotalSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    public static String getSDAvailableSize(Context context) {
       /* File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getBlockSizeLong();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);*/
        StatFs fs = new StatFs(Environment.getDataDirectory().getPath());
        return Formatter.formatFileSize(context, (fs.getAvailableBytes()));
    }
}
