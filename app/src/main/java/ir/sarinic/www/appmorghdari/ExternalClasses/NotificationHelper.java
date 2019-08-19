package ir.sarinic.www.appmorghdari.ExternalClasses;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import ir.sarinic.www.appmorghdari.R;

public class NotificationHelper extends ContextWrapper {


    private static final String CHANEL_ID = "ir.sarinic.www.appmorghdari.ExternalClasses";
    private static final String CHANEL_NAME = "notification channel";
    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    private void createChannels() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.GREEN);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            getmanager().createNotificationChannel(channel);
        }
    }


    public NotificationManager getmanager() {
        if (manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String body) {
            return new Notification.Builder(getApplicationContext(),CHANEL_ID)
                    .setContentText(body)
                    .setContentTitle(title)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);

    }
}
