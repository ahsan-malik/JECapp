package j.e.c.com.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import j.e.c.com.MainActivity;
import j.e.c.com.Others.Prefrence;
import j.e.c.com.R;

public class FirebaseMessageService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "Bestmarts";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //displayNotification(this, remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        //displayNotification(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Prefrence.saveFcmToken(s, FirebaseMessageService.this);
        //Log.d("token", s);
    }

    void displayNotification(Context context, String title, String text) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "technopoints_id")
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.appicon)
                .setAutoCancel(true);
        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(1, mBuilder.build());
    }

    void sendNotification(RemoteMessage remoteMessage) {

        //Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        Map data = remoteMessage.getData();
        //String title = (String) data.get("title");
        //String body = (String) data.get("body");
        //String targetFragment = (String) data.get("target");
        String title = (String) data.get("title");
        String message = (String) data.get("message");
        String id = (String) data.get("id");

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //resultIntent.putExtra("target", targetFragment);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.appicon)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setNumber(10)
                .setTicker(message)
                .setContentTitle(title)
                .setContentText(message)
                .setContentInfo("Info")
                .setContentIntent(pendingIntent);
        notificationManager.notify(1, notificationBuilder.build());
    }

}
