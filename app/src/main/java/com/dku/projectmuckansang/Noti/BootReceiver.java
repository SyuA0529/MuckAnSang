package com.dku.projectmuckansang.Noti;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //set alarm to start service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //set decreaser alarm
        Calendar decreaseCalender = Calendar.getInstance();
        decreaseCalender.setTimeInMillis(System.currentTimeMillis());
        decreaseCalender.set(Calendar.HOUR_OF_DAY, 0);

        Intent intentDecreaseService = new Intent(context, DecreaseService.class);
        PendingIntent alarmDecreaseIntent = PendingIntent.getService(context, 0, intentDecreaseService, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, decreaseCalender.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmDecreaseIntent);

        //set notification alarm
        Calendar notiCalender = Calendar.getInstance();
        notiCalender.setTimeInMillis(System.currentTimeMillis());
        notiCalender.set(Calendar.HOUR_OF_DAY, 18);

        Intent intentNotiService = new Intent(context, NotiService.class);
        PendingIntent alarmNotiIntent = PendingIntent.getService(context, 0, intentNotiService, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, notiCalender.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmNotiIntent);
    }
}