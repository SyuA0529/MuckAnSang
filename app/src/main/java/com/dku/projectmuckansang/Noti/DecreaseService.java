package com.dku.projectmuckansang.Noti;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.dku.projectmuckansang.Database.DatabaseHelper;

public class DecreaseService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        helper.updateProductPeriod();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
