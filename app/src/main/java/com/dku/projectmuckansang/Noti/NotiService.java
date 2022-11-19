package com.dku.projectmuckansang.Noti;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dku.projectmuckansang.MainActivity;
import com.dku.projectmuckansang.R;

public class NotiService extends Service {
    private static String CHANNEL_ID = "MuckAnSang";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if(intent.getAction() != null && intent.getAction().equals("CANCEL")) {
            notificationManager.cancel(123);
        }
        else {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("먹않상")
                    .setContentText("오늘 버릴 항목을 확인하세요!")
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .addAction(makeCancelButtonInNoti("CANCEL"));

            notificationManager.notify(123, builder.build());
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            serviceChannel.enableVibration(true);
            serviceChannel.setDescription("Channel for MuckAnSang");
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private NotificationCompat.Action makeCancelButtonInNoti(String action) {
        Intent intent = new Intent(getApplicationContext(), NotiService.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent ,0);
        NotificationCompat.Action notiActoin = new NotificationCompat.Action.Builder(
                R.drawable.ic_launcher_foreground,
                "CANCEL",
                pendingIntent
        ).build();
        return notiActoin;
    }
}