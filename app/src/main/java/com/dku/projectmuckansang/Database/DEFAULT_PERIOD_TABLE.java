package com.dku.projectmuckansang.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DEFAULT_PERIOD_TABLE {
    private static final String CREATE_TABLE =
            "create table if not exists DEFAULT_PERIOD ( " +
                    "categoryID     integer PRIMARY KEY autoincrement, " +
                    "bigCategory    text    not null, " +
                    "detailCategory text    , " +
                    "period         integer not null" +
                    ")";

    private static final String[] INITIALIZING_TABLE = {
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('육류', '소고기', 5)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('육류', '돼지고기', 14)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('육류', '닭고기', 4)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('육류', '소세지', 365)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('육류', '베이컨', 7)",

            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('채소류', '나물', 4)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('채소류', '구황작물', 180)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('채소류', '쌈', 3)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('채소류', '샐러드', 3)",

            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('유제품', '치즈', 70)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('유제품', '요거트', 30)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('유제품', '우유', 50)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('유제품', '버터', 210)",


            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('난류', '계란', 35)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('난류', '메추리알', 35)",


            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('콩류', '두부', 90)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('콩류', '순두부', 90)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('콩류', '연두부', 90)",

            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('소스류', '', 180)",
            "insert into DEFAULT_PERIOD (bigCategory, detailCategory, period) values ('음료류', '', 70)",
    };

    public static void createTable(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
        Cursor cursor = database.rawQuery("select * from DEFAULT_PERIOD", null);
        if(cursor.getCount() == 0) {
            for (String init : INITIALIZING_TABLE) {
                database.execSQL(init);
            }
        }
    }

    //Checked
    public static int getCategoryID(SQLiteDatabase database, String bigCategory, String detailCategory) {
        Cursor cursor = database.rawQuery(
                "select categoryID from DEFAULT_PERIOD " +
                        "where " + "bigCategory = '" + bigCategory + "' " +
                        " and detailCategory = '" + detailCategory + "'",
                null
        );
        if(cursor.getCount() > 1 || cursor.getCount() == 0) return -1;
        cursor.moveToLast();
        return cursor.getInt(0);
    }

    //Checked
    public static String[] getBigCategoryList(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("select distinct bigCategory from DEFAULT_PERIOD", null);
        int categoryCount = cursor.getCount();
        String[] bigCategoryList = new String[categoryCount];
        for (int i = 0; i < categoryCount; i++) {
            cursor.moveToNext();
            bigCategoryList[i] = cursor.getString(0);
        }
        return bigCategoryList;
    }

    //Checked
    public static int[] getBigCategoryIDList(SQLiteDatabase database, String bigCategory) {
        Cursor cursor = database.rawQuery("select categoryID from DEFAULT_PERIOD where bigCategory = '" + bigCategory + "'", null);
        int[] detailCategoryList = new int[cursor.getCount()];
        for (int i = 0; i < detailCategoryList.length; i++) {
            cursor.moveToNext();
            detailCategoryList[i] = cursor.getInt(0);
        }
        return detailCategoryList;
    }

    public static int getDefaultPeriod(SQLiteDatabase database, int categoryID) {
        Cursor cursor = database.rawQuery(
                "select period from DEFAULT_PERIOD " +
                        "where categoryID = " + categoryID,
                null
        );
        if(cursor.getCount() < 1) return -1;
        cursor.moveToNext();
        return cursor.getInt(0);
    }
}
