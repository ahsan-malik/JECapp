package j.e.c.com.services;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import j.e.c.com.Others.Helper;
import j.e.c.com.Others.Prefrence;

public class FirebaseMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Helper.displayNotification(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Prefrence.saveFcmToken(s, FirebaseMessageService.this);
    }

}
