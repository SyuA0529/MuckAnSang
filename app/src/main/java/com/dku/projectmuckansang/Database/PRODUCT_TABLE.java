package com.dku.projectmuckansang.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PRODUCT_TABLE {
    private static final String TABLE_NAME = "PRODUCT";
    private static final String CREATE_TABLE =
            "create table if not exists " + TABLE_NAME + " ( " +
                "productID      integer PRIMARY KEY autoincrement, " +
                "productName    text    not null," +
                "productCount   integer not null, " +
                "categoryID     integer not null, " +
                "FOREIGN KEY( categoryID ) REFERENCES DEFAULT_PERIOD( categoryID ) " +
            ")";

    public static void createTable(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    //Checked
    public static void insertItem(SQLiteDatabase database, int categoryID, String productName, int productCount) {
        database.execSQL(
                "insert into " + TABLE_NAME + " (categoryID, productName, productCount) " +
                        "values(" + categoryID + ", '" + productName + "', " + productCount +  ")"
        );
    }

    //Checked
    public static int getLastProductID(SQLiteDatabase database, int categoryID, String productName, int productCount) {
        Cursor cursor = database.rawQuery(
                "select productID from " + TABLE_NAME +
                        " where categoryID = " + categoryID + " and " +
                        "productName = '" + productName + "' and " +
                        "productCount = " + productCount,
                null
        );

        int count = cursor.getCount();
        if(count == 0) return -1;
        cursor.moveToLast();
        return cursor.getInt(0);
    }

    public static void deleteItem(SQLiteDatabase database, int productID) {
        database.execSQL(
                "delete from PRODUCT where productID = " + productID
        );
    }
}