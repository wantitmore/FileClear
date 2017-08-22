package com.konka.fileclear.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by user001 on 2017-8-15.
 */

public class SearchUtil {

    private static final String TAG = "SearchUtil";

    public static ArrayList<String> getSupportFileList(Context context, String[] searchFileSuffix ) {
        ArrayList<String> searchFileList;
        if( null == context || null == searchFileSuffix || searchFileSuffix.length == 0 ){
            return null;
        }

        String searchPath = "";
        int length = searchFileSuffix.length;
        for( int index = 0; index < length; index++ ){
            searchPath += ( MediaStore.Files.FileColumns.DATA + " LIKE '%" + searchFileSuffix[ index ] + "' " );
            if( ( index + 1 ) < length ){
                searchPath += "or ";
            }
        }

        searchFileList = new ArrayList<>();
        Uri uri = MediaStore.Files.getContentUri("external");
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Files.FileColumns.DATA },searchPath, null, null);
            Log.d(TAG, "getSupportFileList: search is" + searchPath);
        }catch (Exception e) {
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
                        searchFileList.add(new String(filepath.getBytes("UTF-8")));
                        Log.d(TAG, "getSupportFileList filepath:"+filepath);
                    } catch (UnsupportedEncodingException e) {
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
}
