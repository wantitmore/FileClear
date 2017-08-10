package com.konka.fileclear.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user001 on 2017-8-10.
 */

public class OneKeyClearDB extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;

    public OneKeyClearDB(Context context) {
        super(context, "file_clear.db", null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OneKeyClearTable.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
