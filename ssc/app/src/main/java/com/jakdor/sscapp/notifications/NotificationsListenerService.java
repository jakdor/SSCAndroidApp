package com.jakdor.sscapp.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.jakdor.sscapp.MainActivity;
import com.jakdor.sscapp.Model.NotificationHistory;
import com.jakdor.sscapp.R;
import com.jakdor.sscapp.SplashActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Notification.DEFAULT_LIGHTS;
import static android.app.Notification.DEFAULT_VIBRATE;

public class NotificationsListenerService extends GcmListenerService {

    private final String CLASS_TAG = "NotificationsService";
    private static int massageId = 666;

    @Override
    public void onCreate() {
        super.onCreate();
        initChannels(getBaseContext());
    }

    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        super.onMessageReceived(s, bundle);
        Log.i(CLASS_TAG, "received message: " + bundle.toString());

        saveNotification(bundle.getString("title"), bundle.getString("body"));
        sendNotification(bundle.getString("title"), bundle.getString("body"));
    }

    private void sendNotification(String title, String body) {
        Context context = getBaseContext();

        PendingIntent contentIntent;
        if(MainActivity.appSleeping) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("notifDisp", 1);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            contentIntent = PendingIntent.getActivity(this, 0, intent , 0);
        }
        else {
            contentIntent = null;
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                .setDefaults(DEFAULT_VIBRATE | DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.favicon).setContentTitle(title)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setContentIntent(contentIntent)
                .setContentText(body)
                .setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        massageId++;
        mNotificationManager.notify(massageId, mBuilder.build());
    }

    private void saveNotification(String title, String message){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN);
        String time = sdf.format(new Date());

        NotificationHistory notification = new NotificationHistory(title, message, time);
        notification.save();
    }

    private void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "SSCapp",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("SSC notifications");
        notificationManager.createNotificationChannel(channel);
    }
}
