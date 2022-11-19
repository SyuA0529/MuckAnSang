package com.dku.projectmuckansang.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "MuckAnSang";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase writableDatabase;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        writableDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        USER_TABLE.createTable(sqLiteDatabase);
        DEFAULT_PERIOD_TABLE.createTable(sqLiteDatabase);
        PRODUCT_TABLE.createTable(sqLiteDatabase);
        PRODUCT_PERIOD_TABLE.createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String[] getBigCategoryList() {
        return DEFAULT_PERIOD_TABLE.getBigCategoryList(writableDatabase);
    }

    public ArrayList<ProductData> getSpecificCategoryProductList(String bigCategory) {
        // get big category ID list
        int[] categoryIDList = DEFAULT_PERIOD_TABLE.getBigCategoryIDList(writableDatabase, bigCategory);
        ArrayList<ProductData> specificCategoryProductList = new ArrayList<>();

        // for each category ID
        for (int i = 0; i < categoryIDList.length; i++) {
            //get cur category's products
            Cursor cursor = writableDatabase.rawQuery("select PRODUCT.productID, PRODUCT.productName, PRODUCT.productCount, PRODUCT_PERIOD.remainingPeriod " +
                            "from PRODUCT, PRODUCT_PERIOD " +
                            "where PRODUCT.productID = PRODUCT_PERIOD.productID " +
                            "and PRODUCT.categoryID = " + categoryIDList[i]
                    , null);

            int curProductListLen = cursor.getCount();
            for (int j = 0; j < curProductListLen; j++) {
                cursor.moveToNext();
                specificCategoryProductList.add(new ProductData(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3)
                ));
            }
        }

        return specificCategoryProductList;
    }

    public ArrayList<ProductData> getProductListBetweenSpecificPeriod(int start, int end) {
        Cursor cursor = writableDatabase.rawQuery(
                "select PRODUCT.productID, PRODUCT.productName, PRODUCT.productCount, PRODUCT_PERIOD.remainingPeriod " +
                        "from PRODUCT, PRODUCT_PERIOD " +
                        "where PRODUCT.productID = PRODUCT_PERIOD.productID " +
                        "and PRODUCT_PERIOD.remainingPeriod >= " + start +
                        " and PRODUCT_PERIOD.remainingPeriod < " + end,
                null);
        int productCount = cursor.getCount();
        ArrayList<ProductData> productList = new ArrayList<>();
        for (int i = 0; i < productCount; i++) {
            cursor.moveToNext();
            productList.add(new ProductData(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3)
            ));
        }
        return productList;
    }

    public ArrayList<ProductData> getProductListOverSpecificPeriod(int start) {
        Cursor cursor = writableDatabase.rawQuery(
                "select PRODUCT.productID, PRODUCT.productName, PRODUCT.productCount, PRODUCT_PERIOD.remainingPeriod " +
                        "from PRODUCT, PRODUCT_PERIOD " +
                        "where PRODUCT.productID = PRODUCT_PERIOD.productID " +
                        "and PRODUCT_PERIOD.remainingPeriod >= " + start,
                null);

        int productCount = cursor.getCount();
        ArrayList<ProductData> productList = new ArrayList<>();
        for (int i = 0; i < productCount; i++) {
            cursor.moveToNext();
            productList.add(new ProductData(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3)
            ));
        }
        return productList;
    }

    public void deleteProductById(int productID) {
        writableDatabase.execSQL(
                "delete from PRODUCT where productID = " + productID
        );
    }

    //Checked
    public void insertProduct(int categoryID, String productName, int productCount, int remainingPeriod) {
        PRODUCT_TABLE.insertItem(writableDatabase, categoryID, productName, productCount);
        int productID = PRODUCT_TABLE.getLastProductID(writableDatabase, categoryID, productName, productCount);
        PRODUCT_PERIOD_TABLE.insertItem(writableDatabase, productID, remainingPeriod);
    }

    //
    public int getCategoryID(String bigCategory, String detailCategory) {
        return DEFAULT_PERIOD_TABLE.getCategoryID(writableDatabase, bigCategory, detailCategory);
    }

    public int getDefaultPeriod(int categoryID) {
        return DEFAULT_PERIOD_TABLE.getDefaultPeriod(writableDatabase, categoryID);
    }

    // ERROR
    public ArrayList<ProductData> getTrashListNyCategory(String bigCategory) {
        ArrayList<ProductData> productList = getSpecificCategoryProductList(bigCategory);
        ArrayList<ProductData> returnList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            if(productList.get(i).getRemainingPeriod() < 0) {
                returnList.add(productList.get(i));
            }
        }
        return returnList;
    }

    public void updateProductPeriod() {
        PRODUCT_PERIOD_TABLE.updateAllItemPeriod(writableDatabase);
    }
}