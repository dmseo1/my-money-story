package com.example.seodongmin.expediturerecord;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * Created by seodongmin on 2017-05-15.
 */

public class Broadcast_InputAlarm extends BroadcastReceiver {

    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;


    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, init.class), PendingIntent.FLAG_ONE_SHOT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ico_sun).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setContentTitle("My Money Story").setContentText("즐거운 하루 되셨나요? 오늘 사용한 내역을 입력해보세요 ^^")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

        notificationmanager.notify(1, builder.build());
    }

}
