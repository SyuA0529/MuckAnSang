package com.dku.projectmuckansang.Database;

import android.database.sqlite.SQLiteDatabase;

public class USER_TABLE {
    private static final String CREATE_TABLE =
            "create table if not exists USER ( " +
                    "userCode INTEGER NOT NULL primary key, " +
                    "nickName text not null " +
                    ")";

    public static void createTable(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }
}