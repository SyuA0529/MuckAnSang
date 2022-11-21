package com.dku.projectmuckansang.Database;

import android.database.sqlite.SQLiteDatabase;

public class PRODUCT_PERIOD_TABLE {
    private static final String TABLE_NAME = "PRODUCT_PERIOD";
    private static final String CREATE_TABLE =
            "create table if not exists "+ TABLE_NAME +" ( " +
                "productID          integer PRIMARY KEY, " +
                "remainingPeriod    integer not null, " +
                "FOREIGN KEY( productID ) REFERENCES PRODUCT( categoryID ) ON DELETE CASCADE " +
            ")";

    public static void createTable(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    public static void insertItem(SQLiteDatabase database, int productID, int remainingPeriod) {
        database.execSQL(
                "insert into " + TABLE_NAME + "( productID, remainingPeriod)" +
                        "values(" + productID + ", " + remainingPeriod + ")"
        );
    }

    public static void updateAllItemPeriod(SQLiteDatabase database) {
        database.execSQL(
                "update PRODUCT_PERIOD set remainingPeriod = remainingPeriod - 1 "
        );
    }
}