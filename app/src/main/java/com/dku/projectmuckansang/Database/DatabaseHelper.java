package com.dku.projectmuckansang.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "";
    public static final int DB_VERSION = 1;

    public static final String[] TABLE_NAME_LIST = {

    };
    public static final String[][] TABLE_ATTR_LIST = {
            { },
    };

    private static final String[] CREATE_TABLES = {

    };


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for (String createTable: CREATE_TABLES) {
            sqLiteDatabase.execSQL(createTable);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        for (int j = 0; j < TABLE_ATTR_LIST.length; j++) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LIST[j]);
            sqLiteDatabase.execSQL(CREATE_TABLES[j]);
        }
    }
}
