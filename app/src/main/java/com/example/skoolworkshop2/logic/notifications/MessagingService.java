package com.example.skoolworkshop2.logic.notifications;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

import com.example.skoolworkshop2.dao.localDatabase.LocalDb;
import com.example.skoolworkshop2.dao.localDatabase.entities.Notification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = MessagingService.class.getSimpleName();
    public static String messageData = "";

    @Override
    public void onNewToken(@NonNull String token) {

        Log.d(TAG, "Token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Room.databaseBuilder(this, LocalDb.class, "LocalDb");
        LocalDb.getDatabase(getApplication()).getNotificationDAO().insertNotification(new Notification("title", "description", "url", false));

//        messageData = remoteMessage.getData().toString();

        System.out.println("Notification received");
        System.out.println("---");
        System.out.println(remoteMessage.getCollapseKey());
        System.out.println(remoteMessage.getData());
        System.out.println(remoteMessage.getFrom());
        System.out.println(remoteMessage.getMessageId());
        System.out.println(remoteMessage.getMessageType());
        System.out.println(remoteMessage.getNotification());
        System.out.println(remoteMessage.getOriginalPriority());
        System.out.println(remoteMessage.getPriority());
        System.out.println(remoteMessage.getSenderId());
        System.out.println(remoteMessage.getSentTime());
        System.out.println(remoteMessage.getTo());
        System.out.println(remoteMessage.getTtl());
        System.out.println("---");

        Log.d(TAG, "Message: " + remoteMessage.toString());
    }
}
