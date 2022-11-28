package com.dku.projectmuckansang.Database;

import androidx.annotation.NonNull;

public class ProductData {
    private int mProductID;
    private String mProductName;
    private int mProductCount;
    private int mRemainingPeriod;

    public ProductData(int productID, String productName, int productCount, int remainingPeriod) {
        mProductID = productID;
        mProductName = productName;
        mProductCount = productCount;
        mRemainingPeriod = remainingPeriod;
    }

    public int getProductID() {
        return mProductID;
    }

    public int getRemainingPeriod() {
        return mRemainingPeriod;
    }

    public String getProductName() {
        return mProductName;
    }

    public int getProductCount() {
        return mProductCount;
    }

    @NonNull
    @Override
    public String toString() {
        return mProductName + "   개수: " + mProductCount + "개   남은 날짜: " + mRemainingPeriod;
    }
}