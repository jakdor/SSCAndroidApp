package com.jakdor.sscapp.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.jakdor.sscapp.MainActivity;
import com.jakdor.sscapp.R;
import com.jakdor.sscapp.SplashActivity;

import static android.app.Notification.DEFAULT_LIGHTS;
import static android.app.Notification.DEFAULT_VIBRATE;

public class NotificationsListenerService extends GcmListenerService {

    private final String CLASS_TAG = "NotificationsService";
    private static int massageId = 666;

    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        super.onMessageReceived(s, bundle);
        Log.i(CLASS_TAG, "received message: " + bundle.toString());

        sendNotification(bundle.getString("title"), bundle.getString("body"));
    }

    private void sendNotification(String title, String body) {
        Context context = getBaseContext();

        PendingIntent contentIntent = null;
        if(MainActivity.appSleeping) {
            contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class), 0);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setDefaults(DEFAULT_VIBRATE | DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.favicon).setContentTitle(title)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setContentIntent(contentIntent)
                .setContentText(body);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        massageId++;
        mNotificationManager.notify(massageId, mBuilder.build());
    }
}
