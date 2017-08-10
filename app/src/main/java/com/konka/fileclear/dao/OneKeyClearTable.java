package com.konka.fileclear.dao;

/**
 * Created by user001 on 2017-8-10.
 */

public class OneKeyClearTable {
    public static final String TABLE_NAME = "OneKeyClear";

    public static final String LAST_ONE_KEY_CLEAR_TIME = "_last_one_key_clear_time";
    public static final String LAST_ONE_KEY_CLEAR_SPACE = "_last_one_key_clear_space";
    public static final String TOTAL_ONE_KEY_CLEAR_SPACE = "_total_one_key_clear_space";

    public static final String SQL_CREATE_TABLE = "create table "
            + TABLE_NAME + "("
            + "id integer primary key autoincrement, "
            + LAST_ONE_KEY_CLEAR_TIME + " text, "
            + LAST_ONE_KEY_CLEAR_SPACE + " integer, "
            + TOTAL_ONE_KEY_CLEAR_SPACE + " integer);";

}
